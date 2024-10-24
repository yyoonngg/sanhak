package com.project.sanhak.login.service;

import com.project.sanhak.login.domain.OAuthAttributes;
import com.project.sanhak.domain.user.OAuthToken;
import com.project.sanhak.login.dto.UserProfile;
import com.project.sanhak.login.repository.LoginUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final LoginUserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;  // OAuth2AuthorizedClientService 주입

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 사용자 프로필 생성
        UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);
        userProfile.setProvider(registrationId);

        // accessToken 가져오기
        String accessToken = userRequest.getAccessToken().getTokenValue();

        // refreshToken 가져오기
        String refreshToken = null;
        Map<String, Object> additionalParameters = userRequest.getAdditionalParameters();
        if (additionalParameters.containsKey("refresh_token")) {
            refreshToken = (String) additionalParameters.get("refresh_token");
        }

        // expireDate 가져오기
        LocalDateTime expireDate = null;
        if (userRequest.getAccessToken().getExpiresAt() != null) {
            expireDate = LocalDateTime.ofInstant(
                    userRequest.getAccessToken().getExpiresAt(),
                    ZoneId.systemDefault()
            );
        } else if (additionalParameters.containsKey("expires_in")) {
            int expiresIn = Integer.parseInt(additionalParameters.get("expires_in").toString());
            expireDate = LocalDateTime.now().plusSeconds(expiresIn);
        }

        // UserProfile에 토큰 정보 설정
        userProfile.setAccessToken(accessToken);
        userProfile.setRefreshToken(refreshToken);
        userProfile.setExpireDate(expireDate);  // 만료 시간 설정

        // 사용자 정보를 DB에 업데이트하거나 저장
        updateOrSaveUser(userProfile);

        // 사용자 정보를 반환
        return oAuth2User;
    }


    // refreshToken을 OAuth2AuthorizedClient에서 가져오는 메서드
    private String getRefreshToken(OAuth2UserRequest userRequest, String principalName) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                userRequest.getClientRegistration().getRegistrationId(),
                principalName); // OAuth2User의 이름을 사용

        return authorizedClient != null && authorizedClient.getRefreshToken() != null
                ? authorizedClient.getRefreshToken().getTokenValue()
                : null;
    }

    public Map<String, Object> getCustomAttribute(String registrationId,
                                                  String userNameAttributeName,
                                                  Map<String, Object> attributes,
                                                  UserProfile userProfile) {
        Map<String, Object> customAttribute = new ConcurrentHashMap<>();

        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", userProfile.getUsername());
        customAttribute.put("email", userProfile.getEmail());

        return customAttribute;
    }

    // OAuthToken 저장 또는 업데이트하는 로직
    public OAuthToken updateOrSaveUser(UserProfile userProfile) {
        // 사용자가 이미 존재하면 정보를 업데이트하고, 없으면 새로 생성
        OAuthToken user = userRepository
                .findUserByEmailAndProvider(userProfile.getEmail(), userProfile.getProvider())
                .map(value -> value.updateUser(
                        userProfile.getUsername(),
                        userProfile.getEmail(),
                        userProfile.getProvider(),
                        userProfile.getAccessToken(),
                        userProfile.getRefreshToken(),
                        String.valueOf(userProfile.getExpireDate())  // 토큰 만료 시간 계산
                ))
                .orElse(userProfile.toEntity());  // 새로운 OAuthToken 엔티티 생성

        return userRepository.save(user);  // DB에 저장
    }

}
