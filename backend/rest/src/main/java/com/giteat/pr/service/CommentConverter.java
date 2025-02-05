package com.giteat.pr.service;

import com.giteat.pr.dto.CustomCommentDto;
import com.giteat.pr.dto.FileCommentDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CommentConverter {

    /**
     * 프론트에서 받은 body를 Gitlab 형식에 맞춰 return하는 함수
     * @param request
     * @return FileCommentDto
     */
    public FileCommentDto converToGitLabFormat(CustomCommentDto request){
        FileCommentDto.Position position = new FileCommentDto.Position();
        position.setBaseSha(request.getBaseSha());
        position.setStartSha(request.getStartSha());
        position.setHeadSha(request.getHeadSha());
        position.setOldPath(request.getOldPath());
        position.setNewPath(request.getNewPath());
        position.setPositionType("text");

        // new_or_old 값에 따라 라인 설정
        if(request.getNewOrOld()==1){ // Old 코드
            position.setNewLine(null);
            position.setOldLine(request.getOldStartLine());
            position.setLineRange(createLineRange(request.getOldStartLine(), request.getOldEndLine(), request.getFileId(), 1));

        } else { // New 코드
            position.setOldLine(null);
            position.setNewLine(request.getNewStartLine());
            position.setLineRange(createLineRange(request.getNewStartLine(), request.getNewEndLine(), request.getFileId(), 2));
        }

        return new FileCommentDto(position, request.getBody());
    }

    /**
     * line_range를 설정하는 함수
     * @param startLine
     * @param endLine
     * @param fileId
     * @return line 범위
     */
    private FileCommentDto.Position.LineRange createLineRange(int startLine, int endLine, String fileId, int NewOrOld){
        FileCommentDto.Position.LineRange lineRange = new FileCommentDto.Position.LineRange();
        lineRange.setStart(new FileCommentDto.Position.LineCode(generateLineCode(fileId, startLine, NewOrOld)));
        lineRange.setEnd(new FileCommentDto.Position.LineCode(generateLineCode(fileId, endLine, NewOrOld)));
        return lineRange;
    }

    private String generateLineCode(String fileId, int line, int NewOrOld){
        if(NewOrOld==1){
            return String.format("%s_%s_null", fileId, line); //Old 코드
        }
        return String.format("%s_null_%s", fileId, line); // New 코드
    }
}
