package com.giteat.security.config;

import com.giteat.security.interceptor.OauthInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * webConfig설정 ,  인터셉터 과련된 요청만 추가
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer{

    private OauthInterceptor oauthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(oauthInterceptor)
                .addPathPatterns("/**") // 모든 요청에 적용
                .excludePathPatterns("/login", "/signup"); // 로그인, 회원가입 제외
        // TODO : exlude 경로는 /api/rest 는 빼도 된다.

    }
}

