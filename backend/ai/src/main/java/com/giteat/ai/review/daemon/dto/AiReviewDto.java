package com.giteat.ai.review.daemon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AiReviewDto {
    int ai_review_id;
    int repo_id;
    int pr_id;
    int ar_status_id;
    String content;
//    String before_code;
//    String after_code;
    String create_at;
}
