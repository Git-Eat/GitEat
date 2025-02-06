package com.giteat.statistics.mapper;

import com.giteat.statistics.dto.ParticipantsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsRepoMapper {
    int getTotalCommit(String repoId);
    List<ParticipantsDto> getParticipants(String repoId);
}
