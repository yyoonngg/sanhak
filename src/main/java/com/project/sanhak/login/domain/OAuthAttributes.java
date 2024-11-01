package com.project.sanhak.login.domain;

import com.project.sanhak.login.dto.UserProfileDTO;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attribute) -> {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername((String) attribute.get("name"));
        userProfileDTO.setEmail((String) attribute.get("email"));
        return userProfileDTO;
    }),

    NAVER("naver", (attribute) -> {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        Map responseValue = (Map) attribute.get("response");
        userProfileDTO.setUsername((String) responseValue.get("name"));
        userProfileDTO.setEmail((String) responseValue.get("email"));
        userProfileDTO.setAccessToken((String) attribute.get("access_token"));
        userProfileDTO.setRefreshToken((String) attribute.get("refresh_token"));


        return userProfileDTO;
    }),

    KAKAO("kakao", (attribute) -> {
        Map account = (Map) attribute.get("kakao_account");
        Map profile = (Map) account.get("profile");
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername((String) profile.get("nickname"));
        userProfileDTO.setEmail((String) account.get("email"));
        userProfileDTO.setAccessToken((String) attribute.get("access_token"));
        userProfileDTO.setRefreshToken((String) attribute.get("refresh_token"));
        return userProfileDTO;
    }),

    GITHUB("github", (attribute) -> {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername((String) attribute.get("login"));
        userProfileDTO.setEmail((String) attribute.get("email"));

        return userProfileDTO;
    });

    private final String registrationId; // 로그인한 서비스(ex) google, naver..)
    private final Function<Map<String, Object>, UserProfileDTO> of; // 로그인한 사용자의 정보를 통하여 UserProfile을 가져옴

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfileDTO> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static UserProfileDTO extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(value -> registrationId.equals(value.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
