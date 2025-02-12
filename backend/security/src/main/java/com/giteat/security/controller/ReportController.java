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
        ResponseEntity<?> response = apiUtl.postReportApi("/report/lighthouse-pipeline" , lighthouseDto);

        return ResponseEntity.ok(response.getBody());
    }

}