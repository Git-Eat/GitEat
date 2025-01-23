package com.giteat.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import jakarta.servlet.http.HttpServletRequest;

@Profile("release") // 배포 환경에서만 활성화
@Configuration
public class ReleaseSecurityConfig {
    private static final String ALLOWED_IP = "192.168.0.100"; // 허용된 IP 주소

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(withDefaults()) // CSRF 기본 설정 유지
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new RequestMatcher() {
                            @Override
                            public boolean matches(HttpServletRequest request) {
                                String remoteAddr = request.getRemoteAddr();
                                return ALLOWED_IP.equals(remoteAddr);
                            }
                        }).permitAll() // 허용된 IP에서만 요청 허용
                        .anyRequest().denyAll()); // 나머지 요청 차단
        return http.build();
    }
}
