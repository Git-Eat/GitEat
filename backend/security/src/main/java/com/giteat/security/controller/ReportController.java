package com.giteat.security.controller;

import com.giteat.security.dto.LighthouseDto;
import com.giteat.security.dto.NotiDto;
import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
@Slf4j
public class ReportController {
    private final ApiUtil apiUtl;

    @PostMapping("/lighthouse-pipeline")
    @Operation(summary = "Lighthouse 요청", description = "Lighthouse 요청")
    public ResponseEntity<?> LighthouseSend(
            @RequestBody LighthouseDto lighthouseDto) {
        log.info("🔹 POST /api/report/lighthouse-pipeline 요청 받음! lighthouseDto: {}", lighthouseDto);

        // ✅ 🔥 각 필드별 로그 출력 (null 체크)
        log.info("▶ gitUrl: {}", lighthouseDto.getGitUrl());
        log.info("▶ branch: {}", lighthouseDto.getBranch());
        log.info("▶ repoId: {}", lighthouseDto.getRepoId());
        log.info("▶ build: {}", lighthouseDto.getBuild());
        log.info("▶ frontendPath: {}", lighthouseDto.getFrontendPath());


        ResponseEntity<?> response = apiUtl.postReportApi("/lighthouse-pipeline" , lighthouseDto);

        return ResponseEntity.ok(response.getBody());
    }

}