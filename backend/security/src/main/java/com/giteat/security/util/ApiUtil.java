package com.giteat.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * REST 요청을 수행하는 Utility 클래스
 */
@Component
@Slf4j
public class ApiUtil {

    private final RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String restURL;

    @Value("${api.report-url}")
    private String reportUrl;

    public ApiUtil() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * restAPI 호출 GET
     * @param url
     * @return
     */
    public ResponseEntity<?> getApi(String url) {
        String fullURL = restURL + url;
        log.info("FULL URL : " + fullURL);

        String accessToken = TokenContext.getAccessToken();
        System.out.println("GET ACCESSTOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(fullURL, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<?> getApi(String url, String accessToken) {
        String fullURL = restURL + url;
        log.info("FULL URL : " + fullURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken); // Authorization 헤더 설정

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(fullURL, HttpMethod.GET, requestEntity, Object.class);
    }


    /**
     * restAPI 호출 POST
     * @param url
     * @param requestBody
     * @return
     */
    public ResponseEntity<?> postApi(String url, Object requestBody) {
        String fullURL = restURL + url;
        log.info("POST 요청 URL: " + fullURL);

        String accessToken = TokenContext.getAccessToken();
        System.out.println("POST ACCESSTOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " +accessToken);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(fullURL, requestEntity, Object.class);

    }

    public ResponseEntity<?> postApi(String url, Object requestBody , String accessToken) {
        String fullURL = restURL + url;
        log.info("POST 요청 URL: " + fullURL);
        log.info("ApiUtil Request Body: {}", requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<?> request = restTemplate.postForEntity(fullURL, requestEntity, Object.class);
        System.out.println("SUCCESS GET DATA");
        System.out.println("request : "  + request);
        System.out.println("request body : " + request.getBody());
//        return ResponseEntity.ok(request.getBody());
        return request;
    }

    /**
     * restAPI 호출 PUT
     * @param url
     * @param requestBody
     * @return
     */
    public ResponseEntity<?> putApi(String url, Object requestBody) {
        String fullURL = restURL + url;
        log.info("PUT 요청 URL: " + fullURL);

        String accessToken = TokenContext.getAccessToken();
        System.out.println("PUT ACCESSTOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(fullURL, HttpMethod.PUT, requestEntity, Object.class);
    }

    public ResponseEntity<?> putApi(String url, Object requestBody, String accessToken) {
        String fullURL = restURL + url;
        log.info("PUT 요청 URL: " + fullURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken); // setBearerAuth 사용

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        // HttpMethod.PUT을 명시적으로 지정해야 함!
        return restTemplate.exchange(fullURL, HttpMethod.PUT, requestEntity, Object.class);
    }


    /**
     * restAPI 호출 DELETE
     * @param url
     * @return
     */
    public ResponseEntity<?> deleteApi(String url, Object requestBody) {
        String fullURL = restURL + url;
        log.info("DELETE 요청 URL: " + fullURL);

        String accessToken = TokenContext.getAccessToken();
        System.out.println("DELETE ACCESSTOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.exchange(fullURL, HttpMethod.DELETE, requestEntity, Void.class);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteApi(String url, Object requestBody , String accessToken) {
        String fullURL = restURL + url;
        log.info("DELETE 요청 URL: " + fullURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken); // Authorization 헤더 설정

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(fullURL, HttpMethod.DELETE, requestEntity, Void.class);
    }

    /**
     * 파일 POST용 api
     * @param url
     * @param file
     * @return
     * @throws IOException
     */
    public ResponseEntity<?> postApiWithFile(String url, MultipartFile file) throws IOException {
        // 파일을 전달할 HttpEntity 생성
        String accessToken = TokenContext.getAccessToken();
        System.out.println("FILE ACCESSTOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + accessToken);

        String fullURL = restURL + url;

        // ✅ MultipartFile을 올바른 방식으로 변환
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        // 외부 API 호출
        return restTemplate.exchange(fullURL, HttpMethod.POST, entity, Map.class);
    }

    /**
     * report 관련
     * restAPI 호출 POST
     * @param url
     * @param requestBody
     * @return
     */
    public ResponseEntity<?> postReportApi(String url, Object requestBody) {
        String fullURL = reportUrl + url;
        log.info("POST 요청 URL: " + fullURL);

        String accessToken = TokenContext.getAccessToken();
        System.out.println("POST ACCESSTOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " +accessToken);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(fullURL, requestEntity, Object.class);

    }
}