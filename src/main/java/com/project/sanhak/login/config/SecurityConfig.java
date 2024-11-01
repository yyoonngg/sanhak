package com.project.sanhak.login.config;

import com.project.sanhak.login.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig{

    private final OAuth2Service oAuth2Service;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // CSRF 보호 설정 비활성화
                .authorizeHttpRequests(authorize -> authorize // 권한 설정
                        .requestMatchers("/", "/oauth2/authorization/**").permitAll() // 로그인 관련 URL 접근 허용
                        .anyRequest().authenticated() // 나머지 요청들은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 리다이렉트
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2Service)) // 사용자 정보 서비스 설정
                        .successHandler(customAuthenticationSuccessHandler()) // 성공 핸들러 설정
                )

                .logout(logout -> logout // 로그아웃 설정
                        .logoutUrl("/logout") // 애플리케이션 로그아웃 URL
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // 소셜 로그아웃 URL
                            String naverLogoutUrl = "https://nid.naver.com/nidlogin.logout";
                            String googleLogoutUrl = "https://accounts.google.com/Logout";
                            String kakaoLogoutUrl = "https://kauth.kakao.com/oauth/logout?client_id=YOUR_CLIENT_ID&logout_redirect_uri=YOUR_REDIRECT_URI";
                            String githubLogoutUrl = "https://github.com/logout";


                            // 사용자가 어떤 소셜 로그인으로 인증했는지에 따라 로그아웃 URL 설정
                            String provider = (String) request.getSession().getAttribute("provider"); // provider 세션 속성에서 가져오기
                            String logoutUrl;

                            switch (provider) {
                                case "google":
                                    logoutUrl = googleLogoutUrl;
                                    break;
                                case "naver":
                                    logoutUrl = naverLogoutUrl;
                                    break;
                                case "kakao":
                                    logoutUrl = kakaoLogoutUrl;
                                    break;
                                case "github":
                                    logoutUrl = githubLogoutUrl;
                                    break;
                                default:
                                    logoutUrl = "/";
                                    break;
                            }

                            response.sendRedirect(logoutUrl); // 로그아웃 URL로 리다이렉트
                        })
                        .logoutSuccessUrl("http://localhost:3000/") // 로그아웃 성공 후 리다이렉트할 URL 설정

                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 애플리케이션 쿠키 삭제
                )

                .build();
    }

    // 사용자 로그인 성공 후 추가 작업을 수행하는 핸들러
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                String provider = oauthToken.getAuthorizedClientRegistrationId();
                request.getSession().setAttribute("provider", provider);
            }
            response.sendRedirect("http://localhost:3000/category");
        };
    }

}
