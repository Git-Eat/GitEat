package com.giteat.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class ApiProxyController {

    // application.properties의 값을 가져옴
    @Value("${api.base-url}")
    private String baseUrl;

    // HTTP 통신을 위한 도구
    private final RestTemplate restTemplate;

    // Spring이 자동으로 RestTemplateBuilder를 주입
    public ApiProxyController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    //     관련 모든 요청 처리
    @GetMapping(" ")
    public ResponseEntity<String> proxyRequest(HttpServletRequest request) {

        // 1. 실제 요청을 보낼 주소를 만든다
        String apiUrl = baseUrl + request.getRequestURI();

        // 2. 원래 요청의 헤더 정보를 새 요청에도 포함시킨다
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames())
                .forEach(headerName -> headers.set(headerName, request.getHeader(headerName)));

        // 3. 헤더를 포함한 새로운 요청 객체를 만든다
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 4. 실제 API 서버로 요청을 보내고 응답 받는다
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class);

        //5. 받은 응답을 클라이언트에게 전달한다
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


}
