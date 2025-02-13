package com.giteat.statistics.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributorsDto {
    private int userId;
    private String name;
    private String userName;
    private String avatarUrl;
    private int totalCommit;
    private int totalMergeRequest;
    private int totalComment;
    List<WeeklyContributorsInfo> weeklyInfo;
//    List<MergeRequestByWeekDto> mergeRequestByWeekList; // PR 통계 List
//    List<CommitByWeekDto> commitByWeekList; // commit 통계 List
//    List<CommentByWeekDto> commentByWeekList; // 댓글 및 대댓글 통계 List
}
