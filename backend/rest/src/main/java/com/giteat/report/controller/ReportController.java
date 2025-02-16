package com.giteat.report.controller;

import com.giteat.report.dto.LighthouseDto;
import com.giteat.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{repoId}")
    public ResponseEntity<?> getLatestLighthouseData(@PathVariable int repoId) {
        Optional<LighthouseDto> latestData = reportService.getLatestLighthouseData(repoId);

        return latestData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build()); // 데이터 없으면 204 반환
    }

    @DeleteMapping("/{repoId}")
    public ResponseEntity<?> delLatestLighthouseData(@PathVariable int repoId) {
        Optional<LighthouseDto> latestData = reportService.delLatestLighthouseData(repoId);

        return latestData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build()); // 데이터 없으면 204 반환
    }
}