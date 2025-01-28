package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int commentId;
    private int prId;
    private String content;
    private String commentType;
    private int depth;
    private String image_name;
    private LocalDateTime createdAt;
}
