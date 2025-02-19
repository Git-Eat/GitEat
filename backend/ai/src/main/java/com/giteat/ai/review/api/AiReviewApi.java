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

    public String generateReview(String beforeCode, String afterCode, List<String> previousReviews, String prDescription) {
        try {
            log.info("Generating AI review");
            HttpHeaders headers = createHeaders();

            String userPrompt = buildUserPrompt(beforeCode, afterCode, previousReviews, prDescription);
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

private String buildUserPrompt(String beforeCode, String afterCode, List<String> previousReviews, String prDescription) {
    StringBuilder prompt = new StringBuilder();

//    // PR 설명 상태 추가
//    if(prDescription == null || prDescription.trim().isEmpty()) {
//        prompt.append("⚠️ Pull Request 설명이 작성되지 않았습니다. 코드 변경사항만으로 리뷰를 진행합니다.\n\n");
//    } else {
//        prompt.append("📝 Pull Request 설명:\n")
//                .append(prDescription)
//                .append("\n\n");
//    }

    // PR 설명 전달
    if(prDescription != null && !prDescription.trim().isEmpty()) {
        prompt.append("Context: ").append(prDescription).append("\n\n");
    }

    // 기본 지침
    prompt.append("다음은 Pull Request의 코드 변경사항입니다. 핵심적인 변경사항만 중점적으로 리뷰해주세요.\n\n");

    // 검토 제외 사항과 중요도 순서
    prompt.append("리뷰 시 다음 사항을 지켜주세요:\n")
            .append("1. 다음은 제외하고 검토:\n")
            .append("   - 주석 변경\n")
            .append("   - 공백이나 포맷팅 변경\n")
            .append("   - import/include/using/require 등 외부 모듈 참조 변경\n")
            .append("   - 설정 파일 변경 (*.xml, *.properties, *.yml, *.yaml, *.json, *.conf, *.config, *.ini)\n")
            .append("   - 문서 파일 변경 (*.md, *.txt, *.rst, *.doc, *.pdf)\n")
            .append("   - 로그 파일 변경 (*.log)\n")
            .append("   - 환경 설정 파일 변경 (.env, .gitignore)\n")
            .append("   - 패키지 관리 파일 변경 (package.json, requirements.txt, build.gradle, pom.xml)\n")
            .append("   - 정적 자원 파일 변경 (*.css, *.scss, *.less, *.svg, *.png, *.jpg, *.gif)\n")
            .append("   - 컴파일된 파일 변경 (*.class, *.pyc, *.o, *.exe)\n")
            .append("   - 테스트 파일 변경 (*test.*, *Test.*, *Spec.*)\n")
            .append("   - 임시 파일 변경 (*.tmp, *.temp, *.swp)\n")
            .append("   - 디펜던시 디렉토리 (node_modules/, vendor/, dist/, build/)\n")
            .append("2. 중요도 순으로 리뷰:\n")
            .append("   - 비즈니스 로직 변경\n")
            .append("   - API 엔드포인트 수정\n")
            .append("   - 데이터베이스 관련 변경\n")
            .append("   - 보안 관련 코드\n\n");

    // 이전 리뷰가 있는 경우
    if (!previousReviews.isEmpty()) {
        prompt.append("이전 분석한 내용은 다음과 같습니다:\n\n");
        for (String review : previousReviews) {
            prompt.append(review).append("\n\n");
        }
        prompt.append("위 내용을 참고하여 추가 변경사항을 분석하고, 전체 내용을 하나의 통합된 리뷰로 작성해주세요.\n\n");
    }

    // 코드 변경사항
    prompt.append("[이전 코드]\n").append(beforeCode).append("\n\n");
    prompt.append("[변경된 코드]\n").append(afterCode).append("\n\n");

    // 리뷰 기준
    prompt.append("다음 기준으로 이전 리뷰 내용과 통합하여 하나의 완성된 리뷰를 작성해주세요.\n")
            .append("각 항목은 이모지와 마크다운을 사용하여 가독성 있게 작성해주세요:\n\n")
            .append("1. 🔍 주요 로직 변경 영향\n")
            .append("2. ⚠️ 잠재적 버그\n")
            .append("3. 🔒 심각한 보안 문제\n")
            .append("4. ⚡ 중요한 성능 문제\n")
            .append("\n각 섹션은 명확하게 구분하고, 중요한 부분은 **강조**해주세요.");

    return prompt.toString();
}

    private Map<String, Object> buildRequestBody(String userPrompt) {
        return Map.of(
                "model", "gpt-3.5",
                "messages", Arrays.asList(
                        Map.of("role", "system", "content",
                                "당신은 10년 이상의 현업 경험을 가진 시니어 개발자입니다. " +
                                        "주니어 개발자의 성장을 위해 기술적인 조언과 함께 실용적인 팁을 제공합니다.\n\n" +
                                        "PR 설명이 제공되면 다음과 같이 활용하세요:\n" +
                                        "- PR 설명에서 언급된 목적과 의도를 고려하여 코드를 리뷰\n" +
                                        "- PR 설명에서 언급된 특정 기능이나 버그 수정 사항에 집중\n" +
                                        "- PR 설명과 실제 코드 변경사항이 일치하는지 확인\n\n" +

                                        "🔍 변경사항 분석:\n" +
                                        "- 주요 변경 내용과 의도\n" +
                                        "- 코드의 영향도\n\n" +

                                        "✨ 장점:\n" +
                                        "- 좋은 구현 방식\n" +
                                        "- 효율적인 코드 패턴\n\n" +

                                        "💡 개선사항:\n" +
                                        "- 구체적인 개선 방법\n" +
                                        "- 코드 예시\n\n" +

                                        "⚠️ 주의사항:\n" +
                                        "- 잠재적 문제점\n" +
                                        "- 성능/보안 고려사항\n\n" +

                                        "📚 참고사항:\n" +
                                        "- 관련 디자인 패턴\n" +
                                        "- 추천 레퍼런스\n\n" +

                                        "리뷰 형식:\n" +
                                        "- 마크다운과 이모지 사용\n" +
                                        "- 중요 부분 **강조**\n" +
                                        "- 구체적 예시 포함\n" +
                                        "- 기술적 설명은 쉽게"),
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

/***/