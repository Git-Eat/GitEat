package com.giteat.webHook.gitLab.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MergeRequestTempDto {
    private int repoId;
    private int prId;
    private int userId;
    private int tempStatus;
}
