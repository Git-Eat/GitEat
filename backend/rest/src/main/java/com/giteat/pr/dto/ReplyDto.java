package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {
    private int reCommentId;
    private int commentId;
    private int repoId;
    private int prId;
    private int userId;
    private String dis_id;
    private String content;
    private int replyType;
    private String imageName;
    private LocalDateTime createAt;
}
