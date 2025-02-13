package com.giteat.statistics.service;

import com.giteat.statistics.dto.CommentTotalDto;
import com.giteat.statistics.dto.ContributorsDto;
import com.giteat.statistics.dto.MergeRequestTotalDto;
import com.giteat.statistics.dto.ParticipantsDto;

import java.util.List;
import java.util.Map;

public interface StatisticsRepoService {
    int getTotalCommit(String repoId);
    List<ParticipantsDto> getParticipants(String repoId);
    MergeRequestTotalDto getMergeReqeustTotal(String repoId);
    CommentTotalDto getCommentTotal(String repoId);
    List<ContributorsDto> getContributors(String repoId);
    Map<String, Object> getLanguages(String repoId, String accessToken);
}
