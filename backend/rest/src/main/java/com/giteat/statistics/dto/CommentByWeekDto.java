package com.giteat.statistics.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentByWeekDto {
    private int week;
    private int commentCount;
}
