package com.giteat.webHook.gitLab.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface GitLabWebHookMapper {
    int getReplyCnt(int commentId);

    int getCommentId(Map<String , Object> mapperMap);
}
