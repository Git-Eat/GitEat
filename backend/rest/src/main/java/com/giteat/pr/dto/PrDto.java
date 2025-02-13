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
    private String title; // mergeRequest 제목
    private String description; // mergeRequest description
    private int userId; // MR 생성자 id
    private String userName; // MR 생성자 프로필
    private String userProfile;
    private String createAt; // mergeRequest시간
    private String targetBranch;
    private String sourceBranch;
    private int isOpened;   // 댓글 달 수 있는지 못다는지 여부
    private String baseSha;
    private String headSha;
    private String startSha;
    private int prType;
}
