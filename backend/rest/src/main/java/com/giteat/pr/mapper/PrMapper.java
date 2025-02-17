package com.giteat.pr.mapper;

import com.giteat.pr.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PrMapper {

    List<PrDto> getPrList(int repoId); // PR 목록 확인
    PrDto getPrById(Map<String, Object> params); // PR 상세정보 확인
    List<CommitDto> getCommitList(Map<String, Object> params); // commit 목록 확인
    CommitDto getCommitById(Map<String, Object> params); // commit 상세 정보 확인

    List<CommentDto> getCommentList(Map<String, Object> params); // 댓글 조회
    int deleteComment(Map<String, Object> params); // 댓글 삭제
    List<CommentDto> getCommentListByCode(Map<String, Object> params);

    List<ReplyDto> showReply(int repoId, int prId, int commentId); // 대댓글 조회
    int deleteReply(Map<String, Object> params);

    List<FileDto> showFileListByPr(Map<String, Object> params);
    List<FileDto> showFileListByCommit(Map<String, Object> params);// 변경 된 파일 목록 확인

    FileDto getFileInfo(int fileId); // 변경 된 코드 확인
    List<ReviewerDto> getReviewer(Map<String, Object> params);
    int updateShaInfo(Map<String, Object> params2);
}
