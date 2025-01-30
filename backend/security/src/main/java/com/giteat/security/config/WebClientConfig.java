package com.giteat.security.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient 설정
 * 다른 rest를 호출하기 위한 설정
 */
@Configuration
public class WebClientConfig {

    public WebClientConfig(WebClient.Builder builder) {
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }




}
