package com.giteat.report.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(auth -> auth.disable())
                .httpBasic(auth -> auth.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/**").permitAll()                                    // 임시로 전부 허용
//                    .requestMatchers("/", "/login", "/reissue").permitAll()
//                   .requestMatchers("/test").hasRole("USER")        // test용
//                    .anyRequest().authenticated()
                );
        return http.build();
    }
}
