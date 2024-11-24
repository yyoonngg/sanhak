package com.project.sanhak.login.service;

import com.project.sanhak.domain.user.OAuthToken;
import com.project.sanhak.domain.user.User;
import com.project.sanhak.domain.user.UserInfo;
import com.project.sanhak.login.domain.OAuthAttributes;
import com.project.sanhak.login.dto.UserProfileDTO;
import com.project.sanhak.login.repository.AuthRepository;
import com.project.sanhak.main.dto.profileDTO;
import com.project.sanhak.main.repository.ProfileRepository;
import com.project.sanhak.main.repository.UserRepository;
import com.project.sanhak.main.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;
    private final AuthRepository userAuthRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        Set scope = userRequest.getClientRegistration().getScopes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // 사용자 프로필 생성
        UserProfileDTO userProfileDTO = OAuthAttributes.extract(registrationId, attributes);
        userProfileDTO.setProvider(registrationId);
        if ("kakao".equals(userProfileDTO.getProvider())) {
            String email = scope.toString();
        }
        // accessToken 가져오기
        String accessToken = userRequest.getAccessToken().getTokenValue();
        // refreshToken 가져오기
        String refreshToken = getRefreshToken(userRequest, oAuth2User.getName());
        // expireDate 가져오기
        LocalDateTime expireDate = getExpireDate(userRequest);
        // UserProfile에 토큰 정보 설정
        userProfileDTO.setAccessToken(accessToken);
        userProfileDTO.setRefreshToken(refreshToken);
        userProfileDTO.setExpireDate(expireDate);
        // 사용자 정보를 DB에 업데이트하거나 저장
        int uid = updateOrSaveUser(userProfileDTO);
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        session.setAttribute("uid", uid);
        return oAuth2User;
    }

    // refreshToken을 OAuth2AuthorizedClient에서 가져오는 메서드
    private String getRefreshToken(OAuth2UserRequest userRequest, String principalName) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                userRequest.getClientRegistration().getRegistrationId(),
                principalName
        );

        return authorizedClient != null && authorizedClient.getRefreshToken() != null
                ? authorizedClient.getRefreshToken().getTokenValue()
                : null;
    }

    // OAuthToken 저장 또는 업데이트하는 로직
    private int updateOrSaveUser(UserProfileDTO userProfileDTO) {
        OAuthToken user = userAuthRepository
                .findUserByEmailAndProvider(userProfileDTO.getEmail(), userProfileDTO.getProvider())
                .map(value -> value.updateUser(
                        userProfileDTO.getUsername(),
                        userProfileDTO.getEmail(),
                        userProfileDTO.getProvider(),
                        userProfileDTO.getAccessToken(),
                        userProfileDTO.getRefreshToken(),
                        String.valueOf(userProfileDTO.getExpireDate())
                ))
                .orElseGet(userProfileDTO::toEntity);

        userAuthRepository.save(user);

        // User 엔티티에도 사용자 정보 저장 및 uid 반환
        User userInfo = userRepository.findByUEmailId(userProfileDTO.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setULastLog(LocalDateTime.now());
                    newUser.setUEmailId(userProfileDTO.getEmail());
                    newUser.setUCreate(LocalDateTime.now());
                    return userRepository.save(newUser);
                });
        int uid = userInfo.getUId();
        UserInfo profile = profileRepository.findByUIuid(userInfo);
        if (profile == null) {
            profileDTO profileDTO = new profileDTO();
            profileDTO.setId(uid);
            profileDTO.setName(userProfileDTO.getUsername());
            profileDTO.setEmail(userProfileDTO.getEmail());
            profileService.updateProfile(uid, profileDTO, "default");
        }
        return uid; // User의 uid 반환
    }

    private LocalDateTime getExpireDate(OAuth2UserRequest userRequest) {
        if (userRequest.getAccessToken().getExpiresAt() != null) {
            return LocalDateTime.ofInstant(userRequest.getAccessToken().getExpiresAt(), ZoneId.systemDefault());
        }
        return LocalDateTime.now().plusSeconds(
                Integer.parseInt(userRequest.getAdditionalParameters().getOrDefault("expires_in", "3600").toString())
        );
    }
}
