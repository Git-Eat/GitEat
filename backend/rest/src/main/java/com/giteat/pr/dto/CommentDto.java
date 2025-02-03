package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private String disId;
    private String content;
    private int commentType;
    private String imageName;
    private LocalDateTime createAt;

    private FileCommentDto.Position position; // 코드 위치 정보

    private List<ReplyShowDto> replyList;  // 대댓글 리스트
}
