package com.giteat.common.gitLab.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GitLabTokenMapper {

    String getAccessToken(String jwtAccessToken);

    String getAccessTokenById(String userId);
}
