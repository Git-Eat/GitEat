package com.giteat.ai;

import com.giteat.ai.dto.FileDto;
import com.giteat.ai.review.daemon.entity.MergeRequestEntity;
import com.giteat.ai.review.daemon.repository.MergeRequestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class GitLabApi {
    private final RestTemplate restTemplate;
    private final String gitlabApiUrl = "https://lab.ssafy.com/api/v4";

    public GitLabApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Raw 파일 내용 가져오기
    public String getRawCode(String projectId, String filePath, String ref) {
        try {
            System.out.println("projectId: " + projectId);
            System.out.println("filePath: " + filePath);
            System.out.println("ref: " + ref);

            // 1. URL 인코딩 (파일 경로에 특수문자가 있을 수 있으므로)
            String encodedFilePath = filePath;
            if(!filePath.contains("%2F")) {
                encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8.toString())
                        .replace("+", "%20");  // 공백 처리
//                        .replace("/", "%2F"); // GitLab API는 경로 구분자는 인코딩하지 않음
            }

            System.out.println("Encoded File Path: " + encodedFilePath);

            // 2. API URL 구성
            URI url = new URI(gitlabApiUrl + "/projects/" + projectId + "/repository/files/" + encodedFilePath + "/raw?ref=" + ref);
//            String url = String.format("%s/projects/%s/files/%s/raw?ref=%s", gitlabApiUrl, projectId, encodedFilePath, ref);

            System.out.println("요청 URL: " + url);

            // 3. 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Private-Token", "UATEgVcVTSsLn7PWao6c");

            System.out.println("gitlabApi 3. 헤더 설정: " + headers);

            // 4. API 요청
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println("gitlabApi 4. API 요청" + entity);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("gitlabApi 4-1. API 요청" + response.getBody());
            // 5. 응답 반환
            return response.getBody();

        } catch (Exception e) {
            System.out.println("파일 내용 못가져옴: " + e.getMessage());
            return "";
        }
    }

    // MR 변경된 파일 목록 조회
    public List<Map<String, Object>> getMergeRequestDiffs(String projectId, String mergeRequestId) {
        try {
            System.out.println("[GitLabApi] MR 변경 파일 조회 시작");
            // URL 구성
            String url = String.format("%s/projects/%s/merge_requests/%s/diffs",
                    gitlabApiUrl, projectId, mergeRequestId);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Private-Token", "UATEgVcVTSsLn7PWao6c");

            // API 요청
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<List> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    List.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.out.println("[GitLabApi] MR 변경 파일 조회 실패: " + e.getMessage());
            return null;
        }
    }

    // 특정 Merge Request 조회 함수 (MR id로 조회)
    public Map<String, Object> getMergeRequestsById(String projectId, int prId, String accessToken) {
        try {
            String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Private-Token", "UATEgVcVTSsLn7PWao6c");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.out.println("[GitLabApi] MR 조회 실패: " + e.getMessage());
            return null;
        }
    }
}
