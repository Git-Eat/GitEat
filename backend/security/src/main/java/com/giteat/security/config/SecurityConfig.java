package com.giteat.security.config;

import com.giteat.security.oauth.handler.OAuthLoginSuccessHandler;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
//security 활성화
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler OAuthLoginSuccessHandler;

    public SecurityConfig(AuthenticationSuccessHandler oAuthLoginSuccessHandler) {
        OAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {

        /*
        * JWT나 OAuth 토큰을 사용하므로 CSRF공격에 안전하므로 비활성화
        * */
        http
                .csrf((csrf) -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable());

        // OAuth2 설정
        http
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/api/oauth/authorization"))
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/api/oauth/gitlab/callback"))
                        .successHandler(OAuthLoginSuccessHandler)
                );

        //로그아웃 설정
        http
                .logout(logout -> logout
                        .logoutUrl("/oauth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        })
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                );

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api").permitAll()
                        .requestMatchers("/api/oauth/gitlab").permitAll()
                        .requestMatchers("/api/oauth/gitlab/callback").permitAll()
                        .requestMatchers("/api/oauth/logout").permitAll()
                        .anyRequest().authenticated());

        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    //CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // 아무규칙 없는 CORS 설정
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // 인증관련 정보 전송 가능. oauth나 jwt사용할 때 필요

        // 어떤 URL에 어떤 CORS 설정을 적용할지
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 CORS configuration 적용
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }


}


