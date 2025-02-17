package com.giteat.report.service;

import com.giteat.report.dto.LighthouseDto;
import java.util.Optional;

public interface ReportService {
    Optional<LighthouseDto> getLatestLighthouseData(int repoId);
    Optional<LighthouseDto> delLatestLighthouseData(int repoId);
}
