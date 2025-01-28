package com.giteat.pr.service;

import com.giteat.pr.dto.*;

import java.util.List;

public interface PrService {

    List<PrDto> getPrList(int repoId); // PR 목록 확인
    PrDto getPrById(int repoId, int prId); // PR 상세정보 확인
    List<CommitDto> getCommitList(int repoId, int prId); // commit 목록 확인
    CommitDto getCommitById(int repoId, int prId, int commitId); // commit 상세 정보 확인

    /* 댓글 관련 함수 */
    List<CommentDto> getCommentList(int repoId, int prId); // 댓글 조회
    int insertComment (CommentDto commentDto); // 댓글 등록
    CommentDto updateComment(CommentDto commentDto); // 댓글 수정
    int deleteComment(int repoId, int prId, int commentId); // 댓글 삭제

    /* 대댓글 관련 함수 */
    List<ReplyDto> showReply(int repoId, int prId, int commentId); // 대댓글 조회
    int insertReply(ReplyDto replyDto); // 대댓글 등록
    ReplyDto updateReply(ReplyDto replyDto); // 대댓글 수정
    int deleteReply(int replyId); // 대댓글 삭제

    /* 파일 변경 관련 함수 */
    List<FileDto> showFileList(int repoId, int prId); // 변경 된 파일 목록 확인
    FileDto showChangedCode(int repoId, int prId, int fileId); // 변경 된 코드 확인
}
