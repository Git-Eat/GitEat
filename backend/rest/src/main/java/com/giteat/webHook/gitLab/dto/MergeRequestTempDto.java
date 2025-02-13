package com.giteat.webHook.gitLab.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MergeRequestTempDto {
    private int repoId;
    private int prId;
    private int prIid;
    private int userId;
    private int tempStatus;
}
