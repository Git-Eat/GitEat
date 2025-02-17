package com.giteat.ai;

import com.giteat.ai.dto.FileDto;
import com.giteat.ai.review.daemon.entity.MergeRequestEntity;
import com.giteat.ai.review.daemon.repository.MergeRequestRepository;
import com.giteat.ai.review.daemon.service.TokenValidationService;
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
    private final TokenValidationService tokenValidationService;
    private final String gitlabApiUrl = "https://lab.ssafy.com/api/v4";

    public GitLabApi(RestTemplate restTemplate, TokenValidationService tokenValidationService) {
        this.restTemplate = restTemplate;
        this.tokenValidationService = tokenValidationService;
    }

    // 토큰 유효성 검증을 위한 헬퍼 메서드
    private String getValidToken(int repoId) {
        List<String> validTokens = tokenValidationService.findValidAccessTokens(repoId);
        if (validTokens.isEmpty()) {
            throw new RuntimeException("No valid access token found for repository: " + repoId);
        }
        return validTokens.get(0); // 첫 번째 유효한 토큰 사용
    }

    /**
     * Raw 파일 내용 가져오기
     * 파일 경로와 커밋 참조를 사용하여 특정 시점의 파일 내용을 조회
     */
    public String getRawCode(String projectId, String filePath, String ref, String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            System.out.println("[GitLabApi] Access Token이 없습니다.");
            return null;
        }

        try {
            System.out.println("projectId: " + projectId);
            System.out.println("filePath: " + filePath);
            System.out.println("ref: " + ref);
            System.out.println("accessToken: " + accessToken);

            // 1. URL 인코딩 (파일 경로에 특수문자가 있을 수 있으므로)
            String encodedFilePath = filePath;
            if(!filePath.contains("%2F")) {
                encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8.toString())
                        .replace("+", "%20");  // 공백 처리
            }

            System.out.println("Encoded File Path: " + encodedFilePath);

            // 2. API URL 구성
            URI url = new URI(gitlabApiUrl + "/projects/" + projectId + "/repository/files/" + encodedFilePath + "/raw?ref=" + ref);

            System.out.println("요청 URL: " + url);

            // 3. 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            System.out.println("gitlabApi 3. 헤더 설정: " + headers);

            // 4. API 요청
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println("gitlabApi 4. API 요청: " + entity);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("gitlabApi 4-1. API 요청 response 성공");
            // 5. 응답 반환
            return response.getBody();

        } catch (Exception e) {
            System.out.println("파일 내용 못가져옴: " + e.getMessage());
            return "";
        }
    }

    // MR 변경된 파일 목록 조회
    public List<Map<String, Object>> getMergeRequestDiffs(String projectId, String mergeRequestId, String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            System.out.println("[GitLabApi] Access Token이 없습니다.");
            return null;
        }
        try {
            System.out.println("Using Access Token: " + accessToken);
            System.out.println("[GitLabApi] MR 변경 파일 조회 시작");
            // URL 구성
            String url = String.format("%s/projects/%s/merge_requests/%s/diffs",
                    gitlabApiUrl, projectId, mergeRequestId);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

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
        if (accessToken == null || accessToken.trim().isEmpty()) {
            System.out.println("[GitLabApi] Access Token이 없습니다.");
            return null;
        }

        try {
            String url = gitlabApiUrl + "/projects/" + projectId + "/merge_requests/" + prId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

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
