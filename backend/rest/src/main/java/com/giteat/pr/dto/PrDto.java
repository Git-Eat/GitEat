package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrDto {
    private int prId;   //pr의 고유 id
    private int repoId; //repository 고유Id
    private int userId; //사용자 고유 id
    private String title; // mergeRequest 제목
    private String description; // mergeRequest description
    private String createAt; // mergeRequest시간
    private int isOpened;   // 댓글 달 수 있는지 못다는지 여부
    private String targetBranch;
    private String sourceBranch;
}
