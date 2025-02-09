package com.giteat.pr.service;

import com.giteat.pr.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PrService {

    List<PrDto> getPrList(int repoId); // PR 목록 확인
    PrDto getPrById(int repoId, int prId); // PR 상세정보 확인
    List<CommitDto> getCommitList(int repoId,int prId); // commit 목록 확인
    CommitDto getCommitById(int repoId,int prId, String commitId); // commit 상세 정보 확인

    /* 댓글 관련 함수 */
    List<CommentDto> getCommentList(int repoId, int prId); // 댓글 조회
    int insertComment (String repoId, String prId, CommentDto commentDto); // 댓글 등록
    int updateComment(int repoId, int prId, CommentDto commentDto); // 댓글 수정
    int deleteComment(String repoId, String prId, String commentId); // 댓글 및 대댓글 삭제

    /* 깃랩에 파일 업로드 */
    Map<String, String> uploadsFile(String repoId, MultipartFile file);

    /* 파일에 댓글 달기*/
    int insertFileComment(String repoId, String prId, CustomCommentDto customCommentDto);

    /* 대댓글 관련 함수 */
    List<ReplyDto> showReply(int repoId, int prId, int commentId); // 대댓글 조회
    int insertReply(String repoId, String prId, String discussionId, ReplyDto replyDto); // 대댓글 등록
    int updateReply(String repoId, String prId, String reCommentId, ReplyDto replyDto); // 대댓글 수정
    int deleteReply(String repoId, String prId, String reCommentId);

    /* 파일 변경 관련 함수 */
    List<FileDto> showFileListByPr(int repoId, int prId); // 변경 된 파일 목록 확인
    List<FileDto> showFileListByCommit(int repoId, int prId, String commitId);
    Map<String, Object> showChangedCode(String repoId, String prId, FileDto fileDto); // 변경 된 코드 확인

    /* 리뷰 참여자 조회 */
    List<ReviewerDto> getReviewer(String repoId, String prId);
}
