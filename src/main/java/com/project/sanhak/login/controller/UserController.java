package com.project.sanhak.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "${cors.allowed.origin}")
@RequestMapping("/api/oauth")
public class UserController {
    @Operation(summary = "로그인 정보 호출")
    @GetMapping("/loginInfo")
    public String getJson(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println(oAuth2User.toString());
        System.out.println("\n");
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);
        System.out.println("\n");
        return attributes.toString();
    }

    @Operation(summary = "로그인 중인지 확인")
    @GetMapping("/status")
    public ResponseEntity<?> getAuthStatus(Authentication authentication, HttpSession session) {
        if (authentication != null && authentication.isAuthenticated()) {
            // 세션에서 uid 가져오기
            Integer uid = (Integer) session.getAttribute("uid");
            if (uid != null) {
                // 로그인 되어 있고, uid가 세션에 존재하면 uid와 인증 상태를 반환
                return ResponseEntity.ok().body(Map.of("authenticated", true, "uid", uid));
            } else {
                // uid가 세션에 없으면, 인증은 되어 있지만 uid가 없다고 반환
                return ResponseEntity.ok().body(Map.of("authenticated", true, "uid", null));
        }
        }
        // 로그인되어 있지 않으면 인증 상태만 반환
        return ResponseEntity.ok().body(Map.of("authenticated", false));
    }

}
