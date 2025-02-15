package com.giteat.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.common.constants.WebHookConstants;
import com.giteat.pr.dto.FileCommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class LabApi {
    private final RestTemplate restTemplate;
    private final String gitlabApiUrl = "https://lab.ssafy.com/api/v4";

    private final ObjectMapper objectMapper;
    public LabApi(RestTemplate restTemplate , ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    /**
     * 최근 정보를 가져오는 함수
     */
    public void getRecentData(){

    }

    /**
     * 파일에 댓글을 등록하는 함수
     * Endpoint : /projects/:id/merge_requests/:merge_iid/discussions
     */
    public Map<String, Object> insertFileComment(String projectId, String prId, Map<String, Object> requestBody, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/discussions";
        System.out.println(requestBody);
        return callPostApiObject(url, accessToken, requestBody);
    }


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
     * 댓글을 삭제하는 함수
     * Endpoint : /projects/:id/merge_requests/:merge_request_iid/notes/:note_id
     */
    public boolean deleteComment(String projectId, String prId, String noteId, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/notes/" + noteId;
        return callDeleteApi(url, accessToken);
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

    /**
     * 파일 업로드 함수
     * Endpoint : /projects/{project_id}/uploads
     */
    public Map<String, String> uploadFile(String projectId, MultipartFile file , String accessToken) throws IOException {
        String url = gitlabApiUrl + "/projects/" +  projectId + "/uploads";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Accept", "application/json");

        // ✅ InputStreamResource → ByteArrayResource로 변경
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename() != null ? file.getOriginalFilename() : "upload.tmp";
            }
        });

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // GitLab API 호출
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);

        // 응답 본문 확인
        JsonNode responseBody = response.getBody();
        if (responseBody == null || !responseBody.has("full_path") || !responseBody.has("markdown")) {
            throw new IOException("GitLab API 응답이 올바르지 않습니다: " + response);
        }

        // 결과 매핑
        Map<String, String> fileData = new HashMap<>();
        fileData.put("full_path", responseBody.get("full_path").asText());
        fileData.put("markdown", responseBody.get("markdown").asText());

        return fileData;
    }

    public Map<String, Object> getLanguages(String repoId, String accessToken){
        String url = gitlabApiUrl + "/projects/" + repoId + "/languages";
        return callGetApiMap(url, accessToken);
    }

    public Map<String, Object> getUser(String accessToken){
        String url = gitlabApiUrl + "/user";
        return callGetApiMap(url, accessToken);
    }

    public List<Map<String ,Object>> getMembers(String projectId, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/members";
        return callGetApiList(url, accessToken);
    }

    // 프로젝트의 Merge Requests 조회함수 (가장 최신 MR만 조회)
    public List<Map<String, Object>> getMergeRequests(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests?per_page=1&sort=desc";
        return callGetApiList(url ,accessToken);
    }

    // 특정 Merge Request 조회 함수 (MR id로 조회)
    public Map<String, Object> getMergeRequestsById(String projectId , String prId, String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/"+prId;
        return callGetApiMap(url ,accessToken);
    }

    // MR id를 기준으로 MR 정보를 조회하는 함수
    public List<Map<String, Object>> getMergeRequestsByPageNation(String projectId, int pageNation, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId +"/merge_requests/?page="+pageNation+"&per_page=100";
        return callGetApiList(url, accessToken);
    }

    // 프로젝트 repository 정보 가져오는 함수
    public Map<String, Object> getRepository(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId;
        return callGetApiMap(url , accessToken);
    }


    //  프로젝트의 Commits 가져오기
    public List<Map<String, Object>> getCommits(String projectId, int mrId, String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + mrId + "/commits";
        return callGetApiList(url, accessToken);
    }


    //  프로젝트의 Discussions(토론) 가져오기
    public List<Map<String, Object>> getDiscussions(String projectId , int prId, String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/discussions";
        return callGetApiList(url, accessToken);
    }



    // PR내 변경된 파일 목록 가져오기 > 이걸 쓸거임
    public List<Map<String, Object>> getFilesByPr(String projectId, int prId, int prPageNation, String accessToken){
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId + "/diffs?page="+prPageNation+"&per_page=20";
        return callGetApi(url, accessToken);
    }

    // Commit 내 변경된 파일 목록 가져오기

    public Map<String , Object> getDiffRefs(String projectId , String iid ,String accessToken){
//        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + iid + "/diff";
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + iid;
        return this.callGetApiMap(url , accessToken);
    }

    public List<Map<String , Object>> getWebHooks(String repoId ,String accessToken){
        String url = gitlabApiUrl + "/projectes" + "/" + repoId + "/hooks";
        return this.callGetApiList(url ,accessToken);
    }

    // 변경된 Raw 코드 가져오는 함수
    public String getRawCode(String projectId, String filePath, String ref, String accessToken)  {
        try {
            URI url = new URI(gitlabApiUrl + "/projects/" + projectId + "/repository/files/" + filePath + "/raw?ref=" + ref);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();  // 예외 메시지를 출력
            return "요청 실패"; // GitLab API 요청 실패 시 빈 문자열 반환
        }
    }


    /**
     * restTemplate를 사용해서 데이터를 요청하는 코드
     * @param url
     * @param accessToken
     * @return
     */
    private List<Map<String, Object>> callGetApi(String url , String accessToken) {
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }


    /**
     * Map Type으로 get
     * @param url
     * @param accessToken
     * @return
     */
    private Map<String, Object> callGetApiMap(String url , String accessToken) {
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        //headers.set("PRIVATE-TOKEN", accessToken);
        System.out.println("callGetApiMap : " + accessToken);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        System.out.println("callGEtApi 호출 완료!!!!");
        return response.getBody();
    }


    /**
     * List 방식으로 get
     * @param url
     * @param accessToken
     * @return
     */
    private List<Map<String, Object>> callGetApiList(String url , String accessToken) {
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        System.out.println("getApiList : " + accessToken);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        System.out.println("callGetAPI 호출완료!!!");
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
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        return response.getBody();
    }

    private Map<String, Object> callPostApiObject(String url, String accessToken, Map<String, Object> requestBody) {
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

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
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Map.class);
        return response.getBody();
    }

    /**
     * restTemplate으로 DELETE 요청하는 함수
     * @param url GitLab API URL
     * @param accessToken GitLab Private Token
     * @return 성공 여부 (true: 성공, false: 실패)
     */
    private boolean callDeleteApi(String url, String accessToken) {
        log.info("CALL URL : " + url);
        log.info("ACCESS_TOKEN : " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        return response.getStatusCode().is2xxSuccessful();
    }


    /**
     * merge request 용 webhook 등록
     * @param repoId
     * @param accessToken
     * @return
     */
    public Map<String,  Object> createMergeRequestWebHook(String repoId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + repoId + "/hooks";
        Map<String , String> body = new HashMap<>();
        body.put("url" , WebHookConstants.WEBHOOK_URL);
        body.put("name" , WebHookConstants.MERGE_REQUEST_NAME);
        body.put("description" , WebHookConstants.MERGE_REQUEST_DESCRIPTION);
        body.put("enable_ssl_verification" , "false");
        body.put("merge_requests_events" , "true");
        body.put("custom_webhook_template" , WebHookConstants.MERGE_REQUEST_CUSTOM_TEMPLATE);
        body.put("push_events" , "false");

        return callPostApi(url , accessToken , body);
    }


    /**
     * comment 용 webhook 등록 함수
     * @param repoId
     * @param accessToken
     * @return
     */
    public Map<String,  Object> createCommentWebHook(String repoId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + repoId + "/hooks";
        Map<String , String> body = new HashMap<>();
        body.put("url" , WebHookConstants.WEBHOOK_URL);
        body.put("name" , WebHookConstants.COMMENT_NAME);
        body.put("description" , WebHookConstants.COMMENT_DESCRIPTION);
        body.put("enable_ssl_verification" , "false");
        body.put("note_events" , "true");
        body.put("custom_webhook_template" , WebHookConstants.COMMENT_CUSTOM_TEMPLATE);
        body.put("push_events" , "false");

        return callPostApi(url , accessToken , body);
    }

}
