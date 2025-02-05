package com.giteat.security.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/*
 * rest 요청을할 때 사용하는 사용하는 util 코드
 *
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
        return restTemplate.postForEntity(fullURL, requestBody, Object.class);
    }

    /**
     * restAPI 호출 PUT
     * @param url
     * @param requestBody
     * @return
     */
    public ResponseEntity<?> putApi(String url, Object requestBody) {
        String fullURL = restURL + url;
        restTemplate.put(fullURL, requestBody);
        return ResponseEntity.ok().build();
    }

    /**
     * restAPI 호출 DELETE
     * @param url
     * @return
     */
    public ResponseEntity<?> deleteApi(String url , String id) {
        String fullURL = restURL + url + "?id=" + id;
        restTemplate.delete(fullURL);
        return ResponseEntity.ok().build();
    }
}
