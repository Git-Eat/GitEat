package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommitDto {
    private String commitId;
    private int repoId;
    private int prId;
    private String content;
    private String commitedAt;
}
