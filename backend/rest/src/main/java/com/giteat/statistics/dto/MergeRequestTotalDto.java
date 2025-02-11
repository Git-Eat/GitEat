package com.giteat.statistics.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergeRequestTotalDto {
    private int totalMergeRequest;
    private List<MergeRequestByUserDto> userList;
}
