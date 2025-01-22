package com.giteat.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Profile("dev") // 개발 환경에서만 활성화
@Configuration
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // 새로운 방식으로 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // 모든 요청 허용
        return http.build();
    }
}
