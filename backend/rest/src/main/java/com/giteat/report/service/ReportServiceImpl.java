package com.giteat.report.service;

import com.giteat.report.dto.LighthouseDto;
import com.giteat.report.entity.LighthouseEntity;
import com.giteat.report.repository.LighthouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional
    @Override
    public Optional<LighthouseDto> delLatestLighthouseData(int repoId) {
        // 최신 데이터 1개 조회
        Pageable pageable = PageRequest.of(0, 1);
        List<LighthouseEntity> latestData = lighthouseRepository.getLatestByRepoId(repoId, pageable);

        if (latestData.isEmpty()) {
            return Optional.empty(); // 데이터가 없으면 빈 Optional 반환
        }

        LighthouseEntity entityToDelete = latestData.get(0); // 최신 데이터 1개 가져오기
        lighthouseRepository.deleteById(entityToDelete.getLhId()); // 해당 데이터 삭제

        return Optional.of(LighthouseDto.fromEntity(entityToDelete)); // 삭제한 데이터 반환
    }



}