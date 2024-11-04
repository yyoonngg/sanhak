package com.project.sanhak.login.config;

import com.project.sanhak.login.service.OAuth2Service;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // CSRF 보호 설정 비활성화
                .authorizeHttpRequests(authorize -> authorize // 권한 설정
                        .requestMatchers("/", "/oauth2/authorization/**").permitAll() // 로그인 관련 URL 접근 허용
                        .anyRequest().authenticated() // 나머지 요청들은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2 // OAuth2 로그인 설정
                        .loginPage("/oauth2/authorization/google") // 명시적으로 OAuth2 로그인 경로 설정 (예: 구글 사용 시)
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
                            // 세션에서 provider 정보 가져오기
                            HttpSession session = request.getSession(false);
                            String provider = (session != null) ? (String) session.getAttribute("provider") : null;
                            // 세션이 없거나 provider가 null일 경우 authentication에서 가져오기 시도
                            if (provider == null && authentication instanceof OAuth2AuthenticationToken) {
                                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                                provider = oauthToken.getAuthorizedClientRegistrationId();
                            }
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

                            request.getSession().invalidate();
                            response.setContentType("text/html");
                            response.getWriter().write("<html><body onload=\"setTimeout(function() { window.location.href = 'http://localhost:3000/category'; }, 3000);\">");
                            response.getWriter().write("<script>window.location.href='" + logoutUrl + "';</script>");
                            response.getWriter().write("<p>Logging out... You will be redirected to the home page shortly.</p>");
                            response.getWriter().write("</body></html>");
                        })
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 애플리케이션 쿠키 삭제
                )
                .exceptionHandling(exception -> exception // 예외 처리 설정
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/oauth2/authorization/google")) // 인증되지 않은 사용자에 대한 리다이렉트 경로 설정
                )
                .build();
    }

    // 사용자 로그인 성공 후 추가 작업을 수행하는 핸들러
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String provider = oauthToken.getAuthorizedClientRegistrationId();
            request.getSession().setAttribute("provider", provider);
            System.out.println("Provider in session after set: " + request.getSession().getAttribute("provider"));

            response.sendRedirect("http://localhost:3000/category"); // 로그인 성공 후 리다이렉트
        };
    }

}
