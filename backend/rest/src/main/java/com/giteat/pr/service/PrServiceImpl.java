package com.giteat.pr.service;

import com.giteat.api.LabApi;
import com.giteat.pr.dto.*;
import com.giteat.pr.mapper.PrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PrServiceImpl")
@RequiredArgsConstructor
public class PrServiceImpl implements PrService{

    private final PrMapper prMapper;
    private final CommentConverter commentConverter;
    private final LabApi gitLabApi;


    @Override
    public List<PrDto> getPrList (int repoId) {
        return prMapper.getPrList(repoId);
    }

    @Override
    public PrDto getPrById(int repoId, int prId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return prMapper.getPrById(params);
    }

    @Override
    public List<CommitDto> getCommitList(int repoId,int prId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return prMapper.getCommitList(params);
    }

    @Override
    public CommitDto getCommitById(int repoId, int prId, String commitId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("commitId", commitId);
        return prMapper.getCommitById(params);
    }

    @Override
    public List<CommentDto> getCommentList(int repoId, int prId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        List<CommentDto> comments = prMapper.getCommentList(params);
        for(CommentDto comment : comments){
            if(comment.getPosition().getNewLine()== null && comment.getPosition().getOldLine() ==null){
                comment.setPosition(null);
            }
        }
        return comments;
    }


    @Override
    public int insertComment(String repoId, String prId, CommentDto commentDto) {
        // GitLab API에 댓글 등록 요청
        Map<String,Object> response = gitLabApi.insertComment(repoId, prId, commentDto.getContent(), "");
        if(response != null) return 200;
        return 404;
    }

    @Override
    public int updateComment(int repoId, int prId, CommentDto commentDto) {
        // GitLab API에 댓글 수정 요청
        Map<String,Object> response = gitLabApi.updateComment(String.valueOf(repoId), String.valueOf(prId),  String.valueOf(commentDto.getCommentId()) ,commentDto.getContent(),"");
        if(response != null) return 200;
        return 404;
    }

    @Override
    public int deleteComment(String repoId, String prId, String commentId) {

        // GitLab API에 댓글 삭제 요청
        boolean response = gitLabApi.deleteComment(repoId, prId, commentId,"");

        // 우리 DB에서도 삭제
        if(response){
            Map<String, Object> params = new HashMap<>();
            params.put("repoId", repoId);
            params.put("prId", prId);
            params.put("commentId", commentId);
            return prMapper.deleteComment(params);
        }
        return 404;
    }

    @Override
    public Map<String, String> uploadsFile(String repoId, MultipartFile file) {

        Map<String, String> fileData;
        try {
            fileData = gitLabApi.uploadFile(repoId, file); // 깃랩 이미지 업로드 API 호출
        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
        return fileData;
    }

    @Override
    public int insertFileComment(String repoId, String prId, CustomCommentDto customCommentDto) {
        // RequestBody 데이터 변환
        FileCommentDto gitLabRequest = commentConverter.converToGitLabFormat(customCommentDto);

        // 깃랩 API에 댓글 등록 요청
        Map<String,Object> response = gitLabApi.insertFileComment(repoId, prId, gitLabRequest, "");
        if(response != null) return 200;
        return 404;
    }

    @Override
    public List<ReplyDto> showReply(int repoId, int prId, int commentId) {
        return prMapper.showReply(repoId, prId, commentId);
    }

    @Override
    public int insertReply(String repoId, String prId, String discussionId, ReplyDto replyDto) {
        Map<String,Object> response = gitLabApi.insertReply(repoId, prId, discussionId, replyDto.getContent(), "");
        if(response != null) return 200;
        return 404;
    }

    @Override
    public int updateReply(String repoId, String prId, String reCommentId, ReplyDto replyDto) {
        Map<String,Object> response = gitLabApi.updateReply(repoId, prId, reCommentId, replyDto.getContent(), "");
        if(response != null) return 200;
        return 404;
    }

    @Override
    public int deleteReply(String repoId, String prId, String reCommentId) {
        // GitLab API에 댓글 삭제 요청
        boolean response = gitLabApi.deleteComment(repoId, prId, reCommentId,"");

        // 우리 DB에서도 삭제
        if(response){
            Map<String, Object> params = new HashMap<>();
            params.put("repoId", repoId);
            params.put("prId", prId);
            params.put("reCommentId", reCommentId);
            return prMapper.deleteReply(params);
        }
        return 404;
    }

    @Override
    public List<FileDto> showFileListByPr(int repoId, int prId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return prMapper.showFileListByPr(params);
    }

    @Override
    public List<FileDto> showFileListByCommit(int repoId, int prId, String commitId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("commitId", commitId);
        return prMapper.showFileListByCommit(params);
    }

    @Override
    public Map<String, Object> showChangedCode(String repoId, String prId, FileDto fileDto, String refType) {

        // 1. DB에서 fileId를 기준으로 commit_id, new_path, old_path 가져오기
        String commitId = fileDto.getCommitId();
        String newPath = fileDto.getNewPath();
        String oldPath = fileDto.getOldPath();
        int status = fileDto.getFileStatus();

        String encodedNewPath = encodePath(newPath);
        String encodedOldPath = encodePath(oldPath);

        String newRawFile = null;
        String oldRawFile = null;

        // 2. refType 기준으로 PR(1), Commit(2)별 파일 조회 구분
        if(refType.equals("1")){
            newRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, fileDto.getSourceBranch());
            if(status==2){  // 수정 된 파일의 경우
                oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, fileDto.getTargetBranch());
            } else if(status==3){ // 삭제 된 파일의 경우
                oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, fileDto.getTargetBranch());
                newRawFile = null;
            }
        } else if(refType.equals("2")){
            newRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, commitId);
            oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, fileDto.getTargetBranch());
        }

        // 3. 해당 파일에 달린 댓글 가져오기 > 얘는 Mapper 호출
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("fileId", fileDto.getFileId());
        List<CommentDto> fileComments = prMapper.getCommentListByCode(params);

        // 결과를 MAP으로 반환
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", fileDto.getFileName());
        result.put("oldCode", oldRawFile);
        result.put("newCode", newRawFile);
        result.put("comments", fileComments);  // 댓글 리스트 추가
        return result;
    }

    @Override
    public List<ReviewerDto> getReviewer(String repoId, String prId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return  prMapper.getReviewer(params);
    }




    // 파일 경로를 URL 인코딩하는 함수
    private String encodePath(String path) {
        try {
            return URLEncoder.encode(path, StandardCharsets.UTF_8).replace("/", "%2F");
        } catch (Exception e) {
            throw new RuntimeException("파일 경로 인코딩 실패: " + path, e);
        }
    }
}
