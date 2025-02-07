package com.giteat.ai.controller;

import com.giteat.api.LabApi;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/ai", produces="application/json; charset=utf8")
public class AiController {

    private final LabApi labApi;
    private final RestTemplate restTemplate;

    public AiController(LabApi labApi, RestTemplate restTemplate) {
        this.labApi = labApi;
        this.restTemplate = restTemplate;
    }

//    @PostMapping("/review")
//    public ResponseEntity<String> reviewCode(@RequestBody ReviewRequest request) {
//        try {
//            String review = aiReviewService.aiReview(request.getBeforeCode(), request.getAfterCode());
//            return ResponseEntity.ok(review);
//        } catch (Exception e) {
//             ("코드 리뷰 처리 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("코드 리뷰 처리 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }

    @PostMapping("/review")
    public ResponseEntity<String> getGpt(@RequestBody String code) {
        String review = labApi.aiReview(code);
        return ResponseEntity.ok(review);
    }
}
