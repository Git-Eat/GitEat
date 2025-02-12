package com.giteat.ai.review.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class AiReviewApi {

    private final RestTemplate restTemplate;

    @Value("${gpt.api.key}")
    private String apiKey;

    @Value("${gpt.api.url}")
    private String gptApiUrl;

    public AiReviewApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateReview(String beforeCode, String afterCode) {
        try {
            log.info("Generating AI review");
            HttpHeaders headers = createHeaders();

            String userPrompt = buildUserPrompt(beforeCode, afterCode);
            Map<String, Object> requestBody = buildRequestBody(userPrompt);

            ResponseEntity<Map> response = restTemplate.exchange(gptApiUrl, HttpMethod.POST, new HttpEntity<>(requestBody, headers), Map.class);

            String result = extractGptResponse(response);
            log.info("AI review generation completed");
            return result;
        } catch (Exception e) {
            log.error("Failed to generate AI review", e);
            return "GPT call failed: " + e.getMessage();
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        return headers;
    }

    private String buildUserPrompt(String beforeCode, String afterCode) {
        return String.format(
                "다음은 코드 변경 전과 변경 후의 코드입니다.\n" +
                        "[이전 코드]\n%s\n\n[변경된 코드]\n%s\n\n" +
                        "이 변경 사항을 검토하고 다음 기준에 따라 피드백을 제공하세요:\n" +
                        "1. 코드 변경의 영향\n" +
                        "2. 잠재적인 버그 및 성능 문제\n" +
                        "3. 보안 취약점\n" +
                        "4. 유지보수성과 가독성 개선\n" +
                        "5. 코드 표준 준수 여부\n" +
                        "6. 불필요한 코드 및 종속성 제거 필요 여부\n",
                beforeCode, afterCode
        );
    }

    private Map<String, Object> buildRequestBody(String userPrompt) {
        return Map.of(
                "model", "gpt-4",
                "messages", Arrays.asList(
                        Map.of("role", "system", "content", "당신은 경험 많은 개발자로서 코드 리뷰를 수행합니다. 다음 사항에 집중하세요:\n" +
                                "1. 코드 구조 변경 및 영향\n" +
                                "2. 성능 및 확장성 문제\n" +
                                "3. 보안 취약점\n" +
                                "4. 코드 품질 및 유지보수성\n" +
                                "5. 코드 표준 준수 여부\n" +
                                "6. 가독성 및 유지보수성 개선 가능성\n" +
                                "7. 불필요한 코드 및 종속성 존재 여부\n"),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "temperature", 0.5
        );
    }

    private String extractGptResponse(ResponseEntity<Map> response) {
        return Optional.ofNullable(response.getBody())
                .map(body -> (List<Map<String, Object>>) body.get("choices"))
                .filter(choices -> !choices.isEmpty())
                .map(choices -> (Map<String, Object>) choices.get(0).get("message"))
                .map(message -> message != null ? (String) message.get("content") : "GPT 응답 없음")
                .orElse("GPT 응답 없음");
    }
}