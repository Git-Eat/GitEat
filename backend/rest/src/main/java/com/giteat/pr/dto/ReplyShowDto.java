package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyShowDto {
    private int reCommentId;
    private int userId;
    private String userName;
    private String avatarUrl;
    private String disId;
    private String content;
    private int reCommentType;
    private String imageName;
    private String createAt;
}
