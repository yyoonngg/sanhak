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
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowCredentials(true);
                    config.addAllowedHeader("*");
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/oauth2/authorization/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // 폼 로그인 비활성화
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2Service))
                        .successHandler(customAuthenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // 로그아웃 처리
                            request.getSession().invalidate();
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().flush();
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Unauthorized");
                        })
                )
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
                String provider = oauthToken.getAuthorizedClientRegistrationId();
                request.getSession().setAttribute("provider", provider);
            }
            response.sendRedirect("http://localhost:3000/category");
        };
    }

}



