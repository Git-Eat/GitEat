package com.giteat.pr.service;

import com.giteat.pr.dto.*;
import com.giteat.pr.entity.CommitEntity;
import com.giteat.pr.entity.PrEntity;

import java.util.List;

public interface PrService {

    List<PrEntity> findByRepoId(int repoId); // PR 목록 확인
    PrEntity getPrById(int repoId, int prId); // PR 상세정보 확인
    List<CommitEntity> getCommitList(int repoId, int prId); // commit 목록 확인
    CommitEntity getCommitById(int repoId, int prId, String commitId); // commit 상세 정보 확인

    /* 댓글 관련 함수 */
    List<CommentDto> getCommentList(int repoId, int prId); // 댓글 조회
    int insertComment (CommentDto commentDto); // 댓글 등록
    int updateComment(CommentDto commentDto); // 댓글 수정
    int deleteComment(int repoId, int prId, int commentId); // 댓글 삭제

    /* 대댓글 관련 함수 */
    List<ReplyDto> showReply(int repoId, int prId, int commentId); // 대댓글 조회
    int insertReply(ReplyDto replyDto); // 대댓글 등록
    int updateReply(ReplyDto replyDto); // 대댓글 수정
    int deleteReply(int replyId); // 대댓글 삭제

    /* 파일 변경 관련 함수 */
    List<FileDto> showFileList(int repoId, int prId); // 변경 된 파일 목록 확인
    FileDto showChangedCode(int repoId, int prId, int fileId); // 변경 된 코드 확인

    int saveRepositoryData(String accessToken , String repositoryId);
}
