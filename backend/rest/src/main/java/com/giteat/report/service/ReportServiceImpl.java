package com.giteat.report.service;

import com.giteat.report.dto.LighthouseDto;
import com.giteat.report.entity.LighthouseEntity;
import com.giteat.report.repository.LighthouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final LighthouseRepository lighthouseRepository;

    public Optional<LighthouseDto> getLatestLighthouseData(int repoId) {
        Pageable pageable = PageRequest.of(0, 1); // 최신 1개만 가져오기
        Page<LighthouseEntity> resultPage = lighthouseRepository.findLatestByRepoId(repoId, pageable);

        return resultPage.getContent().stream().findFirst().map(LighthouseDto::fromEntity);
    }


}