package com.giteat.repo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AiReviewStatusMapper {
    int updateStatus(Map<String, Object> params);
}
