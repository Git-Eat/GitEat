package com.giteat.report.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.report.Dto.LighthouseResult;
import com.giteat.report.Entity.LighthouseEntity;
import com.giteat.report.Service.LighthouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@Slf4j
public class LighthouseController {

    private final LighthouseService service;


    private final String jenkinsUrl;
    private final String jenkinsApiToken;
    private final String jenkinsUser;
    private final String jenkinsApiUrl;


    // HTTP 요청으로 외부 API 또는 다른 서버와 통신할 때 사용되는 Spring의 HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    public LighthouseController(LighthouseService service,
                                @Value("${jenkins.url}") String jenkinsUrl,
                                @Value("${jenkins.api.token}") String jenkinsApiToken,
                                @Value("${jenkins.user}") String jenkinsUser,
                                @Value("${jenkins.job.name}") String jenkinsJobName) {
        this.service = service;
        this.jenkinsUrl = jenkinsUrl;
        this.jenkinsApiToken = jenkinsApiToken;
        this.jenkinsUser = jenkinsUser;
        this.jenkinsApiUrl = jenkinsUrl + "/job/" + jenkinsJobName + "/buildWithParameters?token=report-trigger";


        log.info("🌟 [환경변수 확인] Jenkins URL: {}", jenkinsUrl);
        log.info("🌟 [환경변수 확인] Jenkins API Token: {}", jenkinsApiToken);
        log.info("🌟 [환경변수 확인] Jenkins User: {}", jenkinsUser);
        log.info("🌟 [환경변수 확인] Jenkins Job Name: {}", System.getenv("jenkins.job.name"));
        log.info("🌟 [환경변수 확인] Jenkins API URL: {}", jenkinsApiUrl);
    }

    /**
     * 프론트에서 요청한 Lighthouse 테스트를 Jenkins에 전달하여 실행하는 API
     */
    @Operation(summary = "Lighthouse 테스트 실행", description = "프론트엔드에서 보낸 요청을 Jenkins에 전달하여 Lighthouse 테스트를 실행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pipeline started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Git URL format"),
            @ApiResponse(responseCode = "500", description = "Failed to start pipeline")
    })
    @PostMapping("/lighthouse-pipeline")
    public ResponseEntity<String> handleReactRequest(@RequestBody Map<String, String> request,
                                                     @RequestHeader(value="Authorization") String header) {
        String responseJson;
        try {

            // 필수 값 검증
            if (!request.containsKey("gitUrl") || !request.containsKey("branch") ||
                    !request.containsKey("repoId") || !request.containsKey("build") ||
                    request.get("gitUrl").isBlank() || request.get("branch").isBlank() ||
                    request.get("repoId").toString().isBlank() || request.get("build").isBlank()) {

                responseJson = new ObjectMapper().writeValueAsString(
                        Map.of("error", "❌ Missing required parameters: gitUrl, branch, repoId, build"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);

            }
            String gitUrl = request.get("gitUrl");
            String frontendPath = URLEncoder.encode(request.get("frontendPath"), StandardCharsets.UTF_8.toString());
            String branch = request.get("branch");
            String repoId = request.get("repoId");
            String build = request.get("build"); // npm or yarn

            String accessToken = header.split(" ")[1];

            log.info("▶ Lighthouse 테스트 요청 정보: gitUrl={}, frontendPath={}, accessToken={}, branch={}, repoId={}, build={}, jenkinsApiUrl={}",
                    gitUrl, frontendPath, accessToken, branch, repoId, build, jenkinsApiUrl);

            // Git URL에 Access Token 추가
            String modifiedGitUrl;
            if (gitUrl.startsWith("https://")) {
                String remainingUrl = gitUrl.substring(8); // "https://" 이후의 URL 추출
                modifiedGitUrl = "https://" + accessToken + ":" + accessToken + "@" + remainingUrl;
            } else {
                responseJson = new ObjectMapper().writeValueAsString(
                        Map.of("error", "❌ Invalid Git URL format. Must start with 'https://'"));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
            }

            // Jenkins 요청을 위한 HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Jenkins 인증 (Basic Auth)
            String auth = jenkinsUser + ":" + jenkinsApiToken;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.add("Authorization", authHeader);

            // CSRF 보호를 위한 Crumb Token 요청
            String crumbUrl = jenkinsUrl + "/crumbIssuer/api/json";
            HttpEntity<String> crumbEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> crumbResponse = restTemplate.exchange(crumbUrl, HttpMethod.GET, crumbEntity, Map.class);
            log.info("▶ Jenkins Crumb Request URL: {}", crumbUrl);
            log.info("▶ Jenkins Crumb Response: {}", crumbResponse);

            if (crumbResponse.getStatusCode() != HttpStatus.OK) {
                responseJson = new ObjectMapper().writeValueAsString(
                        Map.of("error", "❌ Failed to get Jenkins Crumb Token. Response: " + crumbResponse.getStatusCode()));

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(responseJson);
            }

            String crumb = crumbResponse.getBody().get("crumb").toString();
            String crumbRequestField = crumbResponse.getBody().get("crumbRequestField").toString();
            headers.add(crumbRequestField, crumb);

            // Jenkins Pipeline 실행을 위한 파라미터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("gitUrl", modifiedGitUrl);
            params.add("frontendPath", frontendPath);
            params.add("branch", branch);
            params.add("repoId", repoId);
            params.add("build", build);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            ResponseEntity<String> jenkinsResponse = restTemplate.postForEntity(jenkinsApiUrl, entity, String.class);

            if (jenkinsResponse.getStatusCode() == HttpStatus.OK || jenkinsResponse.getStatusCode() == HttpStatus.CREATED) {
                log.info("✅ Lighthouse Pipeline 실행 성공");
                responseJson = new ObjectMapper().writeValueAsString(Map.of("message", "Pipeline started successfully"));
                return ResponseEntity.ok(responseJson);
            } else {
                log.error("❌ Jenkins에서 에러 발생: {}", jenkinsResponse.getStatusCode());
                throw new Exception("Jenkins Pipeline 실행 실패! 응답 코드: " + jenkinsResponse.getStatusCode());
            }
        } catch (Exception e) {
            log.error("❌ Failed to start pipeline", e);
            return ResponseEntity.status(500).body("❌ Failed to start pipeline: " + e.getMessage());
        }
    }


    /**
     * Jenkins에서 실행된 Lighthouse 테스트 결과를 수신하여 DB에 저장하는 API
     */
    @Operation(summary = "Lighthouse 테스트 결과 저장", description = "Jenkins에서 Lighthouse 테스트 결과를 수신하고 DB에 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lighthouse results received successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to process Lighthouse results")
    })
    @PostMapping("/lighthouse-results")
    public ResponseEntity<String> receiveLighthouseResults(@RequestBody LighthouseResult lighthouseResult,
                                                           @RequestHeader("repoId") int repoId,
                                                           @RequestHeader("branch") String branch) {
        String responseJson;
        try {
            // 받은 결과를 로깅
            log.info("✅ Lighthouse Results Received:");
            log.info("RepoId: {}", repoId);
            log.info("branch: {}", branch);

            // Performance Scores 로깅
            log.info("\n📊 Performance Scores:");
            log.info("Performance: {}%", lighthouseResult.getPerformance() * 100);
            log.info("Accessibility: {}%", lighthouseResult.getAccessibility() * 100);
            log.info("Best Practices: {}%", lighthouseResult.getBestPractices() * 100);
            log.info("SEO: {}%", lighthouseResult.getSeo() * 100);

            // Metrics(지표) 로깅
            log.info("FCP: {}", lighthouseResult.getFcp());
            log.info("LCP: {}", lighthouseResult.getLcp());
            log.info("TBT: {}", lighthouseResult.getTbt());
            log.info("CLS: {}", lighthouseResult.getCls());
            log.info("SI: {}", lighthouseResult.getSi());
            // 변환 적용 후 반올림
            double fcp = (lighthouseResult.getFcp() != 0)
                    ? Math.round(lighthouseResult.getFcp() / 10.0) / 100.0  // ms → s 변환 후 소수점 1자리 반올림
                    : 0.0;

            double lcp = (lighthouseResult.getLcp() != 0)
                    ? Math.round(lighthouseResult.getLcp() / 10.0) / 100.0  // ms → s 변환 후 소수점 1자리 반올림
                    : 0.0;

            double tbt = (lighthouseResult.getTbt() != 0)
                    ? Math.round(lighthouseResult.getTbt() * 100) / 100.0  // ms 값은 그대로 유지
                    : 0.0;

            double cls = (lighthouseResult.getCls() != 0)
                    ? Math.round(lighthouseResult.getCls() * 100) / 100.0  // CLS 원래 소수 값
                    : 0.0;

            double si = (lighthouseResult.getSi() != 0)
                    ? Math.round(lighthouseResult.getSi() / 10.0) / 100.0  // ms → s 변환 후 소수점 1자리 반올림
                    : 0.0;

            log.info("반올림 후");
            log.info("FCP: {}", fcp);
            log.info("LCP: {}", lcp);
            log.info("TBT: {}", tbt);
            log.info("CLS: {}", cls);
            log.info("SI: {}", si);

            // Lighthouse 결과를 LighthouseEntity 엔티티로 변환
            LighthouseEntity entity = LighthouseEntity.builder()
                    .repositoryId(repoId)
                    .branch(branch)
                    .performance(lighthouseResult.getPerformance() * 100)
                    .accessibility(lighthouseResult.getAccessibility() * 100)
                    .bestPractices(lighthouseResult.getBestPractices() * 100)
                    .seo(lighthouseResult.getSeo() * 100)
                    .fcp(fcp)
                    .lcp(lcp)
                    .tbt(tbt)
                    .cls(cls)
                    .si(si)
                    .build();

            // DB 저장
            service.saveLighthouseResult(entity);
            responseJson = new ObjectMapper().writeValueAsString(
                    Map.of("message", "✅ Lighthouse results received successfully.")
            );
            return ResponseEntity.ok(responseJson);
        } catch (Exception e) {
            log.error("❌ Failed to process Lighthouse results", e);
            try {
                responseJson = new ObjectMapper().writeValueAsString(
                        Map.of("error", "❌ Failed to process Lighthouse results: " + e.getMessage()));
            } catch (Exception jsonException) {
                responseJson = "{\"error\": \"❌ Failed to serialize error message\"}";
            }
            return ResponseEntity.status(500).body(responseJson);
        }
    }
}
