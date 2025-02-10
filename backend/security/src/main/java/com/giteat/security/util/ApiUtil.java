package com.giteat.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
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
        return restTemplate.getForEntity(fullURL, String.class);
    }

    public ResponseEntity<?> getApi(String url , String accessToken) {
        String fullURL = restURL + url;
        log.info("FULL URL : " + fullURL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        return restTemplate.getForEntity(fullURL, String.class);
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
        log.info("ApiUtil Request Body: {}", requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

//        restTemplate.postForEntity(fullURL, requestEntity, Object.class);
//        return ResponseEntity.ok().build();

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.exchange(fullURL, HttpMethod.PUT, requestEntity, Void.class);
        return ResponseEntity.ok().build();
    }

    /**
     * restAPI 호출 DELETE
     * @param url
     * @return
     */
    public ResponseEntity<?> deleteApi(String url, Object requestBody) {
        String fullURL = restURL + url;
        log.info("DELETE 요청 URL: " + fullURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.exchange(fullURL, HttpMethod.DELETE, requestEntity, Void.class);
        return ResponseEntity.ok().build();
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String fullURL = restURL + url;

        // MultipartFile을 HttpEntity로 변환
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());

        HttpEntity<?> entity = new HttpEntity<>(builder.build(), headers);

        // 외부 API 호출
        return restTemplate.exchange(fullURL, HttpMethod.POST, entity, Map.class);
    }
}