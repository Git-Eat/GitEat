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
    private int repoId;
    private int userId;
    private String dis_id;
    private String content;
    private int commentType;
    private String imageName;
    private LocalDateTime createAt;
}
