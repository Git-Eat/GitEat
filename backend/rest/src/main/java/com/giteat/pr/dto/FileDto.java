package com.giteat.pr.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private String fileId;
    private String commitId;
    private int repoId;
    private int prId;
    private String fileName;
    private String oldPath;
    private String newPath;
    private int fileStatus;

    // 브랜치 정보
    private String targetBranch;
    private String sourceBranch;
}
