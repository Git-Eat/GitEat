package com.giteat.pr.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerDto {
    private int userId;
    private String userName;
    private String name;
    private String avatarUrl;
    private int commentType;
}
