package com.giteat.security.controller;

import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class ReviewController {

    private final ApiUtil apiUtil;

    @GetMapping("/review/{repoId}/{prId}")
    @Operation(summary = "AI 리뷰 조회", description = "저장된 AI 리뷰 결과를 조회합니다.")
    public ResponseEntity<?> getReviewList(@PathVariable int repoId,@PathVariable int prId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        System.out.println(params);

        ResponseEntity<String> response =
                (ResponseEntity<String>) apiUtil.getApi("/review/list/"+ repoId + "/" +prId);

        System.out.println("security controller: "+response.getBody());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.getBody());
    }
}
