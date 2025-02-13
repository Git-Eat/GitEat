package com.giteat.statistics.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergeRequestByWeekDto {
    private int week;
    private int mergeRequestCount;
}
