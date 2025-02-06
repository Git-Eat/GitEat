package com.giteat.statistics.service;

import com.giteat.statistics.dto.ParticipantsDto;
import com.giteat.statistics.mapper.StatisticsRepoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StatisticsRepoServiceImpl")
@RequiredArgsConstructor
public class StatisticsRepoServiceImpl implements StatisticsRepoService{

    private final StatisticsRepoMapper statisticsRepoMapper;

    @Override
    public int getTotalCommit(String repoId) {
        return statisticsRepoMapper.getTotalCommit(repoId);
    }

    @Override
    public List<ParticipantsDto> getParticipants(String repoId) {
        return statisticsRepoMapper.getParticipants(repoId);
    }
}
