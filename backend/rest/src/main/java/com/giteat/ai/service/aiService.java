//package com.giteat.ai.service;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class AiReviewService {
//    private static final Logger logger = LoggerFactory.getLogger(AiReviewService.class);
//    private final GptApi gptApi;
//
//    public AiReviewService(GptApi gptApi) {
//        this.gptApi = gptApi;
//    }
//
//    public String aiReview(String beforeCode, String afterCode) {
//        try {
//            String systemPrompt = "You are an AI code reviewer specializing in GitHub and GitLab PR/MR reviews. " +
//                    "Your goal is to analyze the code changes and provide precise and professional code reviews. " +
//                    "Your review should include:\n" +
//                    "1. Summary of changes made.\n" +
//                    "2. Whether the changes improve code quality.\n" +
//                    "3. Potential issues, optimizations, or best practices.\n" +
//                    "4. Constructive feedback with examples where necessary.\n" +
//                    "5. Additional review points for team discussion.";
//
//            String userPrompt = "### 변경 전 코드:\n" + beforeCode +
//                    "\n\n### 변경 후 코드:\n" + afterCode +
//                    "\n\n### 코드 리뷰를 다음 형식으로 제공해:\n" +
//                    "\n- 변경 사항 요약\n" +
//                    "- 코드 개선 여부 분석\n" +
//                    "- 문제점 및 위험 요소\n" +
//                    "- 개선 방법 제안\n" +
//                    "- 추가 리뷰 포인트";
//
//            return gptApi.callGptApi(systemPrompt, userPrompt);
//        } catch (Exception e) {
//            logger.error("코드 리뷰 처리 중 오류", e);
//            return "코드 리뷰 처리 실패: " + e.getMessage();
//        }
//    }
//}
