package com.giteat.pr.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private int fileId;
    private String commitId;
    private String fileName;
    private String oldPath;
    private String newPath;
}
