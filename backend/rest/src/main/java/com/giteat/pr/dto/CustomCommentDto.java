package com.giteat.pr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomCommentDto {
    private String fileId;
    private String baseSha;
    private String startSha;
    private String headSha;
    private String oldPath;
    private String newPath;
    private int newOrOld; // 1: old 기준, 2: new 기준
    private int oldStartLine;
    private int oldEndLine;
    private int newStartLine;
    private int newEndLine;
    private String body;
}
