package com.giteat.ai.review.daemon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AiReviewStatusDto {
    private int arStatusId;
    private int prId;
    private int repo_id;
    private String createAt;
    private int status;  // 0: 미생성, 1: 생성완료
}
