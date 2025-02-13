package com.giteat.pr.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyReturnDto {
    private int reCommentId;
    private int commentId;
    private int repoId;
    private int prId;
    private int userId;
    private String userName;
    private String avatarUrl;
    private String discussionId;
    private String content;
    private int reCommentType;
    private String createAt;
}
