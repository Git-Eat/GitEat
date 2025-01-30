package com.giteat.api;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Component
public class GitLabApi {
    private final RestTemplate restTemplate;
    private final String gitlabApiUrl = "https://lab.ssafy.com/api/v4";
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

    /**
     * 댓글을 등록하는 함수
     * Endpoint : /projects/:id/merge_requests/:merge_request_iid/notes
     */
    public Map<String, Object> insertComment(String projectId, String prId, String content, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/notes";
        Map<String, String> requestBody = Map.of("body", content); // GitLab API에서 요구하는 JSON Body 생성
        return callPostApi(url, accessToken, requestBody);
    }

    /**
     * 댓글을 수정하는 함수
     * Endpoint : /projects/:id/merge_requests/:merge_request_iid/notes/:note_id
     */
    public Map<String, Object> updateComment(String projectId, String prId, String noteId, String content,  String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/notes/" + noteId;
        Map<String, String> requestBody = Map.of("body", content); // GitLab API에서 요구하는 JSON Body 생성
        return callPutApi(url, accessToken, requestBody);
    }

    /**
     * 대댓글을 등록하는 함수
     * Endponint : /projects/{project_id}/merge_requests/{mergerequest_id}/discussions/{discussions_id}/notes
     */
    public Map<String, Object> insertReply(String projectId, String prId, String discussionId, String content, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/discussions/"+ discussionId +"/notes";
        Map<String, String> requestBody = Map.of("body", content); // GitLab API에서 요구하는 JSON Body 생성
        return callPostApi(url, accessToken, requestBody);
    }

    /**
     * 대댓글을 수정하는 함수
     * Endpoint : /projects/{project_id}/merge_requests/{mergerequest_id}/notes/{note_id}
     */
    public Map<String, Object> updateReply(String projectId, String prId, String reCommentId, String content, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/notes/"+ reCommentId;
        Map<String, String> requestBody = Map.of("body", content); // GitLab API에서 요구하는 JSON Body 생성
        return callPutApi(url, accessToken, requestBody);
    }


    // 프로젝트의 Merge Requests 가져오기 예시입니다.
    public List<Map<String, Object>> getMergeRequests(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests";
        return callGetApi(url ,accessToken);
    }


    /**
     * restTemplate으로 요청하는 코드
     * @param url
     * @param accessToken
     * @return
     */
    private List<Map<String, Object>> callGetApi(String url , String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Private-Token", "UATEgVcVTSsLn7PWao6c");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    /**
     * restTemplate으로 POST 요청하는 함수
     * @param url GitLab API URL
     * @param accessToken GitLab Private Token
     * @param requestBody 요청 바디 (JSON 형태의 Map)
     * @return API 응답 데이터 (등록된 댓글 정보)
     */
    private Map<String, Object> callPostApi(String url, String accessToken, Map<String, String> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Private-Token", "UATEgVcVTSsLn7PWao6c");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        return response.getBody();
    }

    /**
     * restTemplate으로 PUT 요청하는 함수
     * @param url GitLab API URL
     * @param accessToken GitLab Private Token
     * @param requestBody 요청 바디 (JSON 형태의 Map)
     * @return API 응답 데이터 (등록된 댓글 정보)
     */
    private Map<String, Object> callPutApi(String url, String accessToken, Map<String, String> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Private-Token", "UATEgVcVTSsLn7PWao6c");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Map.class);
        return response.getBody();
    }

}
