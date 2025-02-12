package com.giteat.review;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list/{repoId}/{prId}")
    @Operation(summary = "AI 리뷰 조회", description = "DB에 저장된 AI 리뷰 결과를 조회합니다.")
    public ResponseEntity<List<AiReviewEntity>> getReview(@PathVariable int repoId, @PathVariable int prId) {
        System.out.println("rest controller - repoId: " + repoId + ", prId: " + prId);
        List<AiReviewEntity> response = reviewService.getReview(repoId, prId);
        System.out.println("rest controller: "+response);
        return ResponseEntity.ok(response);
    }
}
