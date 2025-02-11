package com.giteat.statistics.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentTotalDto {
    private int totalComment;
    private List<CommentByUserDto> userList;
}
