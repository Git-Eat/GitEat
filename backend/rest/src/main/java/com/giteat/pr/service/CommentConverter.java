package com.giteat.pr.service;

import com.giteat.pr.dto.CustomCommentDto;
import com.giteat.pr.dto.FileCommentDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentConverter {

    /**
     * 프론트에서 받은 body를 Gitlab 형식에 맞춰 return하는 함수
     * @param request
     * @return FileCommentDto
     */
    public Map<String, Object> converToGitLabFormat(CustomCommentDto request){
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> position = new HashMap<>();
        requestBody.put("position", position);
        requestBody.put("body", request.getBody());

        position.put("base_sha", request.getBaseSha());
        position.put("start_sha", request.getStartSha());
        position.put("head_sha", request.getHeadSha());
        position.put("old_path", request.getOldPath());
        position.put("new_path", request.getNewPath());
        position.put("position_type", "text");

        // new_or_old 값에 따라 라인 설정
        if(request.getNewOrOld()==1){
            position.put("old_line", request.getOldStartLine()); // Old 코드
        } else {
            position.put("new_line", request.getNewStartLine()); // New 코드
        }

        Map<String, Map<String, Object>> lineRange = new HashMap<>();

        // start 객체 생성
        Map<String, Object> start = new HashMap<>();
        String startLineCode = request.getFileId() + "_" + request.getOldStartLine() + "_" + request.getNewStartLine();
        start.put("line_code", startLineCode);

        // end 객체 생성
        Map<String, Object> end = new HashMap<>();
        String endLineCode = request.getFileId() + "_" + request.getOldEndLine() + "_" + request.getNewEndLine();
        end.put("line_code",endLineCode);

        lineRange.put("start", start);
        lineRange.put("end", end);
        
        return requestBody;
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
