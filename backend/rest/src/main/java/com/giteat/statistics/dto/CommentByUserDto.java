package com.giteat.statistics.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentByUserDto {
    private int userId;
    private int commentCount;
    private String name;
    private String userName;
    private String avatarUrl;
}
