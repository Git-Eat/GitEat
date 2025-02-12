package com.giteat.statistics.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyContributorsInfo {
    private int week;
    private int mergeRequestCount;
    private int commitCount;
    private int commentCount;
}
