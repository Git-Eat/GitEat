package com.giteat.pr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrDto {
    private int prId;
    private int repoId;
    private int userId;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private int isOpened;
    private String targetBranch;
    private String sourceBranch;
}
