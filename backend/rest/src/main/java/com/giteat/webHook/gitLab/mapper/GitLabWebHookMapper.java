package com.giteat.webHook.gitLab.mapper;

import com.giteat.pr.dto.PrDto;
import com.giteat.webHook.gitLab.dto.CommentTempDto;
import com.giteat.webHook.gitLab.dto.MergeRequestTempDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GitLabWebHookMapper {
    int getReplyCnt(int commentId);

    int getCommentId(Map<String , Object> mapperMap);

    int insertMergeRequestTemp(MergeRequestTempDto mergeRequestTempDto);

    int updateMergeRequestData(PrDto prDto);

    int updateMergeRequestStatus(MergeRequestTempDto prTempDto);


    int insertCommentTemp(CommentTempDto coommentTempDto);

    List<MergeRequestTempDto> getPrTemp(String accessToken);


    //comment 댓글 관련 mapper
    List<CommentTempDto> getCommentList(String accessToken);
}
