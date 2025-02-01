package com.giteat.security.config;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SecurityConfig {

//    private final AuthenticationSuccessHandler OAuthLoginSuccessHandler;
//
//    public SecurityConfig(AuthenticationSuccessHandler oAuthLoginSuccessHandler) {
//        OAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
//
//        /*
//        * JWT나 OAuth 토큰을 사용하므로 CSRF공격에 안전하므로 비활성화
//        * */
//        http
//                .csrf((csrf) -> csrf.disable())
//                .cors(Customizer.withDefaults())
//                .formLogin((form) -> form.disable())
//                .httpBasic((basic) -> basic.disable());
//
//        // OAuth2 설정
//        http
//                .oauth2Login(oauth2 -> oauth2
//                                .defaultSuccessUrl("/oauth/success")
////                        .authorizationEndpoint(authorization -> authorization
////                                .baseUri("/oauth2/authorization/gitlab"))
////                        .redirectionEndpoint(redirection -> redirection
////                                .baseUri("/login/oauth2/code/gitlab"))
////                        .successHandler(OAuthLoginSuccessHandler)
//                        .failureHandler((request, response, e) -> {
//                            // 인증 실패 시 처리
//                            log.error("인증 실패: ", e);
//                            response.sendRedirect("/login/oauth2/error");
//
//                        })
//                );
//
//        //로그아웃 설정
//        http
//                .logout(logout -> logout
//                        .logoutUrl("/oauth/logout")
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.setStatus(200);
//                        })
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                );
//
//        //경로별 인가 작업
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers(
//                                "/oauth2/authorization/**",  // 인증 시작 endpoint
//                                "/login/oauth2/code/**", // 리다이렉트 endpoint
//                                "/oauth/logout",               // 로그아웃 endpoint
//                                "/oauth/gitlab",
//                                "/login/gitlab"
//                                ).permitAll()
//                        .requestMatchers("/api/**").permitAll()  // API 엔드포인트
//                        .anyRequest().authenticated()
//                );
//
//        // 디버깅용 로그 추가
//        log.debug("Security configuration loaded successfully");
//        //세션 설정 : STATELESS
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
//
//        return http.build();
//    }


@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            // CSRF 비활성화
            .csrf((csrf) -> csrf.disable())

            // 폼 로그인 비활성화
            .formLogin((form) -> form.disable())

            // HTTP Basic 인증 비활성화
            .httpBasic((basic) -> basic.disable())

            // OAuth2 로그인 비활성화
            .oauth2Login((oauth2) -> oauth2.disable())

            // 모든 요청에 대해 인증 없이 접근 허용
            .authorizeHttpRequests((auth) -> auth
                    .anyRequest().permitAll()
            )

            // 세션 관리 비활성화
            .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

    return http.build();
}



    //CORS 설정
//    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // 아무규칙 없는 CORS 설정
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // 인증관련 정보 전송 가능. oauth나 jwt사용할 때 필요

        // 어떤 URL에 어떤 CORS 설정을 적용할지
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 CORS configuration 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}


