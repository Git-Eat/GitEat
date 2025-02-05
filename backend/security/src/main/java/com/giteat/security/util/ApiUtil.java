package com.giteat.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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


    /**
     * restAPI 호출 POST
     * @param url
     * @param requestBody
     * @return
     */
    public ResponseEntity<?> postApi(String url, Object requestBody) {
        String fullURL = restURL + url;
        log.info("POST 요청 URL: " + fullURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(fullURL, requestEntity, Object.class);
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
}
