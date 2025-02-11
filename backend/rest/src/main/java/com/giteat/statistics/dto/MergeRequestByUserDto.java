package com.giteat.statistics.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergeRequestByUserDto {
    private int userId;
    private int mergeRequestCount;
    private String name;
    private String userName;
    private String avatarUrl;

}
