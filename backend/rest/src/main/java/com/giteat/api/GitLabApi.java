package com.giteat.api;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Component
public class GitLabApi {
    private final RestTemplate restTemplate;
    private final String gitlabApiUrl = "https://gitlab.com/api/v4";
    public GitLabApi(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     * 최근 정보를 가져오는 함수
     */
    public void getRecentData(){

    }
    /**
     * 전체를 가져오는 함수
     */
    public void getAllData(){}


    // 프로젝트의 Merge Requests 가져오기 예시입니다.
    public List<Map<String, Object>> getMergeRequests(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests";
        return callApi(url ,accessToken);
    }


    /**
     * restTemplate으로 요청하는 코드
     * @param url
     * @param accessToken
     * @return
     */
    private List<Map<String, Object>> callApi(String url , String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Private-Token", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }
}
