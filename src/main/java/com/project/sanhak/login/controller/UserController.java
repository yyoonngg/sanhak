package com.project.sanhak.login.controller;

import io.swagger.v3.oas.annotations.Operation;
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
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> getAuthStatus(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(Map.of("authenticated", true));
        }
        return ResponseEntity.ok().body(Map.of("authenticated", false));
    }

}
