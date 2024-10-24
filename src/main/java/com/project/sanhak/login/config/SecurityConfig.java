package com.project.sanhak.login.config;

import com.project.sanhak.login.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // CSRF 보호 설정 사용 X
                .logout(logout -> logout.disable()) // 로그아웃 사용 X
                .formLogin(form -> form.disable()) // 폼 로그인 사용 X
                .authorizeHttpRequests(authorize -> authorize // 권한 설정
                        .requestMatchers("/", "/login").permitAll() // 해당 URL은 인증 절차 생략 가능
                        .anyRequest().authenticated() // 나머지 요청들은 모두 인증 절차 수행해야 함
                )
                .oauth2Login(oauth2 -> oauth2 // OAuth2를 통한 로그인 사용
                        .defaultSuccessUrl("/", true)
                )
                .build();
    }
}
