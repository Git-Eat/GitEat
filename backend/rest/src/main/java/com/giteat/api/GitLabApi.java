package com.giteat.api;

import com.giteat.common.util.GitLabTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GitLabApi {
    private final RestTemplate restTemplate;
    private final String gitlabApiUrl = "https://gitlab.com/api/v4";

    private final GitLabTokenService gitLabToeknSerivce;
    public GitLabApi(RestTemplate restTemplate, GitLabTokenService gitLabToeknSerivce){
        this.restTemplate = restTemplate;
        this.gitLabToeknSerivce = gitLabToeknSerivce;
    }

    /**
     * 최근 정보를 가져오는 함수
     */
    public void getRecentData(){

    }
    /**
     * 전체를 가져오는 함수
     */
    public Map<String , List<Map<String , Object>>> getAllData(String accessToken , String projectId){
        Map<String , List<Map<String , Object>>> repositoryData = new HashMap<>();
        repositoryData.put("mergeRequest" , getMergeRequests(projectId, accessToken));
        repositoryData.put("commits", getCommits(projectId, accessToken));
        repositoryData.put("issues", getIssues(projectId, accessToken));
        repositoryData.put("discussions", getDiscussions(projectId, accessToken));
        repositoryData.put("comments", getComments(projectId, accessToken));
        return repositoryData;
    }


    // 프로젝트의 Merge Requests 가져오기 예시입니다.
    public List<Map<String, Object>> getMergeRequests(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests";
        return callApi(url ,accessToken);
    }

    //  프로젝트의 Commits 가져오기
    public List<Map<String, Object>> getCommits(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/repository/commits";
        return callApi(url , accessToken);
    }

    //  프로젝트의 Issues 가져오기
    public List<Map<String, Object>> getIssues(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/issues";
        return callApi(url , accessToken);
    }

    //  프로젝트의 Discussions(토론) 가져오기
    public List<Map<String, Object>> getDiscussions(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/discussions";
        return callApi(url , accessToken);
    }

    //  프로젝트의 Comments(노트) 가져오기
    public List<Map<String, Object>> getComments(String projectId , String accessToken) {
        String url = gitlabApiUrl + "/projects/" + projectId + "/notes";
        return callApi(url , accessToken);
    }

    /**
     * restTemplate를 사용해서 데이터를 요청하는 코드
     * @param url
     * @param jwtAccessToken
     * @return
     */
    private List<Map<String, Object>> callApi(String url , String jwtAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = gitLabToeknSerivce.getAccessToken(jwtAccessToken);
        headers.set("Private-Token", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }
}
