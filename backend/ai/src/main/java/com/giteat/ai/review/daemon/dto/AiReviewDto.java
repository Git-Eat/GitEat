package com.giteat.ai.review.daemon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AiReviewDto {
    private int aiReviewId;
    private int repoId;
    private int prId;
    private int arStatusId;
    private String content;
    private String baseSha;
    private String headSha;
    private LocalDateTime createAt;
}
