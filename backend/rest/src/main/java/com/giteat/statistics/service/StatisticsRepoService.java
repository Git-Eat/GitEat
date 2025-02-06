package com.giteat.statistics.service;

import com.giteat.statistics.dto.ParticipantsDto;

import java.util.List;

public interface StatisticsRepoService {
    int getTotalCommit(String repoId);
    List<ParticipantsDto> getParticipants(String repoId);
}
