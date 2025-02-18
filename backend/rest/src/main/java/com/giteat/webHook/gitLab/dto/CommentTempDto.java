package com.giteat.webHook.gitLab.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentTempDto {
    private int prId;
    private int repoId;
    private int tempStatus;
    private int userId;
}
