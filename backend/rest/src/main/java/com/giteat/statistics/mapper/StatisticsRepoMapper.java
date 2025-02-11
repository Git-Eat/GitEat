package com.giteat.statistics.mapper;

import com.giteat.statistics.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsRepoMapper {
    int getTotalCommit(String repoId);
    List<ParticipantsDto> getParticipants(String repoId);
    int getTotalMergeRequest(String repoId);
    List<MergeRequestByUserDto> getMergeRequestByUser(String repoId);
    int getTotalComment(String repoId);
    List<CommentByUserDto> getCommentByUser(String repoId);

    // Contributors 통계용 //
    List<WeeklyContributorsInfo> getWeeklyInfo(Map<String, Object> params);
    int getPrCountByUser(Map<String, Object> params);
    int getCommitCountByUser(Map<String, Object> params);
    int getCommentCountByUser(Map<String, Object> params);
}
