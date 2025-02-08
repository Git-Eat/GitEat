package com.giteat.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GitLabApi {
    private final RestTemplate restTemplate;
    private final String gitlabApiUrl = "https://lab.ssafy.com/api/v4";

    public GitLabApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 파일 내용 가져오기
    public String getFileContent(String projectId, String filePath, String ref) {
        try {
            // 1. URL 인코딩 (파일 경로에 특수문자가 있을 수 있으므로)
            String encodedFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");




        } catch (Exception e) {
            throw new RuntimeException(e);
        }





        return projectId;
    }

}
