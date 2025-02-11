package com.giteat.statistics.mapper;

import com.giteat.statistics.dto.CommentByUserDto;
import com.giteat.statistics.dto.MergeRequestByUserDto;
import com.giteat.statistics.dto.ParticipantsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsRepoMapper {
    int getTotalCommit(String repoId);
    List<ParticipantsDto> getParticipants(String repoId);
    int getTotalMergeRequest(String repoId);
    List<MergeRequestByUserDto> getMergeRequestByUser(String repoId);
    int getTotalComment(String repoId);
    List<CommentByUserDto> getCommentByUser(String repoId);
}
