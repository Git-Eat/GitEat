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
    private int userId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String target_branch;
    private String source_branch;
    private int is_opened;
}
