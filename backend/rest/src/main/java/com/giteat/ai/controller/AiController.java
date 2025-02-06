package com.giteat.ai.controller;

import com.giteat.api.LabApi;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final LabApi labApi;
    private final RestTemplate restTemplate;

    public AiController(LabApi labApi, RestTemplate restTemplate) {
        this.labApi = labApi;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/review")
    public ResponseEntity<String> getGpt(@RequestBody String code) {
        String review = labApi.aiReview(code);
        return ResponseEntity.ok(review);
    }
}
