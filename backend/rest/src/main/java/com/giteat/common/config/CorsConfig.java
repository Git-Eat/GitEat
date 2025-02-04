package com.giteat.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/rest/**")  // `/api/` 경로만 허용
                .allowedOrigins("http://i12b108.p.ssafy.io:8801", "http://i12b108.p.ssafy.io") // 허용할 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type") // 허용할 헤더
                .allowCredentials(true); // 쿠키 포함 허용
    }
}
