package com.giteat.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LighthouseDto {
    private String gitUrl;
    private String branch;
    private int repoId;
    private String build;
    private String frontendPath;
}
