package com.giteat.ai.review.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AiReviewApi {

    private final RestTemplate restTemplate;
    private static final int MAX_TOKENS = 4096;  // GPT-4 토큰 제한

    @Value("${gpt.api.key}")
    private String apiKey;

    @Value("${gpt.api.url}")
    private String gptApiUrl;


    public AiReviewApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 코드 구조를 표현하는 내부 클래스
    private static class CodeStructure {
        String type;          // "class", "method", "if", "for", "while" 등
        String name;          // 클래스명, 메서드명 등
        int startLine;
        int endLine;
        String content;
        List<CodeStructure> children = new ArrayList<>();

        @Override
        public String toString() {
            return String.format("%s: %s (lines %d-%d)", type, name, startLine, endLine);
        }
    }

    // AI 리뷰 생성 메서드
    public String generateReview(String beforeCode, String afterCode) {
        try {
            log.info("AI 리뷰 생성 시작");

            // HTTP 헤더 설정
            HttpHeaders headers = createHeaders();

            // 코드 변경사항 분석
            String changes = analyzeChanges(beforeCode, afterCode);
            log.debug("분석된 변경사항: {}", changes);

            // 프롬프트 생성
            String userPrompt = buildUserPrompt(changes, beforeCode, afterCode);
            Map<String, Object> requestBody = buildRequestBody(userPrompt);

            // GPT API 호출
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(gptApiUrl, HttpMethod.POST, request, Map.class);

            String result = extractGptResponse(response);
            log.info("AI 리뷰 생성 완료");
            return result;

        } catch (Exception e) {
            log.error("AI 리뷰 생성 실패", e);
            return "GPT 호출 실패: " + e.getMessage();
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        return headers;
    }

    private String detectSignificantChanges(List<CodeStructure> before, List<CodeStructure> after) {
        StringBuilder changes = new StringBuilder();

        // 클래스 레벨 변경 감지
        for (CodeStructure afterClass : after) {
            CodeStructure beforeClass = findMatchingStructure(before, afterClass);

            if (beforeClass == null) {
                changes.append("### 새로운 클래스 추가\n")
                        .append("- 클래스명: ").append(afterClass.name).append("\n")
                        .append("- 주요 메서드:\n");
                afterClass.children.stream()
                        .filter(c -> c.type.equals("method"))
                        .forEach(m -> changes.append("  * ").append(m.name).append("\n"));
                continue;
            }

            // 메서드 레벨 변경 감지
            for (CodeStructure afterMethod : afterClass.children) {
                if (afterMethod.type.equals("method")) {
                    CodeStructure beforeMethod = findMatchingStructure(beforeClass.children, afterMethod);
                    if (beforeMethod == null) {
                        changes.append("### 새로운 메서드 추가\n")
                                .append("- 클래스: ").append(afterClass.name).append("\n")
                                .append("- 메서드: ").append(afterMethod.name).append("\n");
                    } else if (!methodsAreIdentical(beforeMethod, afterMethod)) {
                        changes.append("### 메서드 변경 감지\n")
                                .append("- 클래스: ").append(afterClass.name).append("\n")
                                .append("- 메서드: ").append(afterMethod.name).append("\n")
                                .append(analyzeMethodChanges(beforeMethod, afterMethod));
                    }
                }
            }
        }

        return changes.toString();
    }

    private String analyzeMethodChanges(CodeStructure before, CodeStructure after) {
        StringBuilder analysis = new StringBuilder();

        // 제어 흐름 변경 감지
        Set<String> beforeControls = extractControlStructures(before);
        Set<String> afterControls = extractControlStructures(after);

        if (!beforeControls.equals(afterControls)) {
            analysis.append("- 제어 흐름 변경:\n");
            Set<String> newControls = new HashSet<>(afterControls);
            newControls.removeAll(beforeControls);
            for (String newControl : newControls) {
                analysis.append("  * 새로운 ").append(newControl).append(" 구문 추가\n");
            }
        }

        // 중요 패턴 변경 감지
        for (Pattern pattern : IMPORTANT_PATTERNS) {
            boolean beforeHas = pattern.matcher(before.content).find();
            boolean afterHas = pattern.matcher(after.content).find();
            if (beforeHas != afterHas) {
                analysis.append("- 중요 패턴 변경: ")
                        .append(pattern.pattern())
                        .append(afterHas ? " 추가\n" : " 제거\n");
            }
        }

        return analysis.toString();
    }

    private String buildUserPrompt(String changes, String beforeCode, String afterCode) {
        return String.format("""
            ### 변경 사항 분석:
            %s
            
            ### 리뷰 요청 사항:
            1. 위 변경사항들의 영향도 분석
            2. 잠재적인 버그나 성능 이슈 검토
            3. 보안 취약점 검토
            4. 코드 품질 및 유지보수성 분석
            5. 개선 제안사항
            """, changes);
    }

    private Map<String, Object> buildRequestBody(String userPrompt) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");

        String systemPrompt = """
            당신은 시니어 개발자로서 코드 리뷰를 수행합니다. 다음 사항에 중점을 두고 리뷰해주세요:
            1. 코드의 구조적 변경과 그 영향
            2. 성능 및 확장성 이슈
            3. 보안 취약점
            4. 비즈니스 로직의 정확성
            5. 코드 품질 및 클린 코드 원칙
            
            형식적인 변경(공백, 주석 등)은 무시하고, 핵심적인 변경사항에 집중해주세요.
           
            """;

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemPrompt));
        messages.add(Map.of("role", "user", "content", userPrompt));

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.5);

        return requestBody;
    }

    private String extractGptResponse(ResponseEntity<Map> response) {
        Map responseBody = response.getBody();
        if (responseBody == null) return "GPT 응답 처리 실패";

        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices == null || choices.isEmpty()) return "GPT 응답 없음";

        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        if (message == null) return "GPT 응답 형식 오류";

        return (String) message.getOrDefault("content", "GPT 응답 내용 없음");
    }

    // 유틸리티 메서드들
    private String extractClassName(String line) {
        return line.replaceAll(".*class\\s+(\\w+).*", "$1");
    }

    private String extractMethodName(String line) {
        return line.replaceAll(".*\\s+(\\w+)\\s*\\(.*", "$1");
    }

    private String extractControlType(String line) {
        return line.replaceAll("(\\w+)\\s*\\(.*", "$1");
    }

    private boolean methodsAreIdentical(CodeStructure before, CodeStructure after) {
        String beforeContent = removeCommentsAndWhitespace(before.content);
        String afterContent = removeCommentsAndWhitespace(after.content);
        return beforeContent.equals(afterContent);
    }

    private String removeCommentsAndWhitespace(String code) {
        // 한 줄 주석 제거
        code = code.replaceAll("//.*$", "");
        // 여러 줄 주석 제거
        code = code.replaceAll("/\\*.*?\\*/", "");
        // 공백 제거
        return code.replaceAll("\\s+", "");
    }

    private Set<String> extractControlStructures(CodeStructure structure) {
        return structure.children.stream()
                .filter(child -> child.type.matches("if|for|while|switch"))
                .map(child -> child.type)
                .collect(Collectors.toSet());
    }

    private CodeStructure findMatchingStructure(List<CodeStructure> structures, CodeStructure target) {
        return structures.stream()
                .filter(s -> s.type.equals(target.type) && s.name.equals(target.name))
                .findFirst()
                .orElse(null);
    }
}