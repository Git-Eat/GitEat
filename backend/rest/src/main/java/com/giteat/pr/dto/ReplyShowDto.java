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
    private String disId;
    private String content;
    private int replyType;
    private String imageName;
    private LocalDateTime createAt;
}
