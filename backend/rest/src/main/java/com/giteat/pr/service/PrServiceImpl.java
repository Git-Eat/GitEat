package com.giteat.pr.service;

import com.giteat.api.GitLabApi;
import com.giteat.pr.dto.*;
import com.giteat.pr.mapper.PrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PrServiceImpl")
@RequiredArgsConstructor
public class PrServiceImpl implements PrService{

    private final PrMapper prMapper;
    private final CommentConverter commentConverter;
    private final GitLabApi gitLabApi;


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
        return prMapper.getCommentList(params);
    }

    @Override
    public int insertComment(int repoId, int prId, CommentDto commentDto) {
        // GitLab API에 댓글 등록 요청
        Map<String,Object> response = gitLabApi.insertComment(String.valueOf(repoId), String.valueOf(prId), commentDto.getContent(), "");
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
    public Map<String, String> showChangedCode(String repoId, String prId, int fileId) {

        // 1. DB에서 fileId를 기준으로 commit_id, new_path, old_path 가져오기
        FileDto file = prMapper.getFileInfo(fileId);
        if(file == null) return null;

        String commitId = file.getCommitId();
        String newPath = file.getNewPath();
        String oldPath = file.getOldPath();

        // 2. 파일 경로 인코딩
        String encodedOldPath = encodePath(oldPath);
        String encodedNewPath = encodePath(newPath);

        // 3. 깃랩 API 호출 (변경 전 후 코드 가져오기)
        String oldFileContent = gitLabApi.getRawCode(repoId, encodedOldPath, commitId,"");
        String newFileContent = gitLabApi.getRawCode(repoId, encodedNewPath, commitId,"");

        // 4. 결과를 Map으로 반환
        Map<String, String> result = new HashMap<>();
        result.put("fileName", file.getFileName());
        result.put("oldCode", oldFileContent);
        result.put("newCode", newFileContent);

        return result;
    }

    @Override
    public int saveRepositoryData(String accessToken , String repositoryId){
        // 만들어 놓은 함수 호출
        // 호출하고 형식 맞춰서 데이터베이스 저장
        // 저장하고 나 값으로 형식 맞춰서 데이터 넣기
        Map<String ,List<Map<String , Object>>> repositoryData = gitLabApi.getAllData(accessToken, repositoryId);
        System.out.println("repositoryData : " + repositoryData);
        return 1;
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
