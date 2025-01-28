package com.giteat.pr.mapper;

import com.giteat.pr.dto.FileDto;
import com.giteat.pr.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrMapper {
    List<ReplyDto> showReply(int repoId, int prId, int commentId); // 대댓글 조회
    int insertReply(ReplyDto replyDto); // 대댓글 등록
    ReplyDto updateReply(ReplyDto replyDto); // 대댓글 수정
    int deleteReply(int replyId); // 대댓글 삭제
    List<FileDto> showFileList(int repoId, int prId); // 변경 된 파일 목록 확인
    FileDto showChangedCode(int repoId, int prId, int fileId); // 변경 된 코드 확인
}
