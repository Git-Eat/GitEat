package com.giteat.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.giteat.report.entity.LighthouseEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LighthouseDto {

    private int lhId;  // Lighthouse 데이터 ID
    private int repositoryId;  // 해당 저장소 ID
    private String branch;  // 브랜치명
    private double performance;  // 성능 점수
    private double accessibility;  // 접근성 점수
    private double bestPractices;  // Best Practices 점수
    private double seo;  // SEO 점수
    private double fcp;  // First Contentful Paint
    private double lcp;  // Largest Contentful Paint
    private double tbt;  // Total Blocking Time
    private double cls;  // Cumulative Layout Shift
    private double si;  // Speed Index


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;  // 생성 시간

    // **Entity → DTO 변환 메서드**
    public static LighthouseDto fromEntity(LighthouseEntity entity) {
        return LighthouseDto.builder()
                .lhId(entity.getLhId())
                .repositoryId(entity.getRepositoryId())
                .branch(entity.getBranch())
                .performance(entity.getPerformance())
                .accessibility(entity.getAccessibility())
                .bestPractices(entity.getBestPractices())
                .seo(entity.getSeo())
                .fcp(entity.getFcp())
                .lcp(entity.getLcp())
                .tbt(entity.getTbt())
                .cls(entity.getCls())
                .si(entity.getSi())
                .createAt(entity.getCreateAt())
                .build();
    }
}