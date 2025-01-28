package com.giteat.pr.mapper;

import com.giteat.pr.dto.CommentDto;
import com.giteat.pr.dto.FileDto;
import com.giteat.pr.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PrMapper {
    List<CommentDto> getCommentList(Map<String, Object> params); // 댓글 조회
    int insertComment(CommentDto commentDto); // 댓글 등록
    int updateComment(CommentDto commentDto); // 댓글 수정
    int deleteComment(Map<String, Object> params); // 댓글 삭제

    List<ReplyDto> showReply(int repoId, int prId, int commentId); // 대댓글 조회
    int insertReply(ReplyDto replyDto); // 대댓글 등록
    int updateReply(ReplyDto replyDto); // 대댓글 수정
    int deleteReply(int replyId); // 대댓글 삭제

    List<FileDto> showFileList(int repoId, int prId); // 변경 된 파일 목록 확인
    FileDto showChangedCode(int repoId, int prId, int fileId); // 변경 된 코드 확인
}
