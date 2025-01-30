package com.giteat.pr.service;

import com.giteat.api.GitLabApi;
import com.giteat.pr.dto.*;
import com.giteat.pr.entity.CommitEntity;
import com.giteat.pr.entity.PrEntity;
import com.giteat.pr.mapper.PrMapper;
import com.giteat.pr.repository.PrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PrServiceImpl")
@RequiredArgsConstructor
public class PrServiceImpl implements PrService{

    private final PrMapper prMapper;
    private final PrRepository prRepository;
    private final GitLabApi gitLabApi;


    @Override
    public List<PrEntity> findByRepoId(int repoId) {
        return prRepository.findByRepoId(repoId);
    }

    @Override
    public PrEntity getPrById(int repoId, int prId) {
        return prRepository.getPrById(repoId, prId);
    }

    @Override
    public List<CommitEntity> getCommitList(int repoId, int prId) {
        return prRepository.getCommitList(repoId, prId);
    }

    @Override
    public CommitEntity getCommitById(int repoId, int prId, String commitId) {
        return prRepository.getCommitById(repoId, prId, commitId);
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
    public int deleteComment(int repoId, int prId, int commentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("commentId", commentId);
        return prMapper.deleteComment(params);
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
    public int deleteReply(int replyId) {
        return prMapper.deleteReply(replyId);
    }

    @Override
    public List<FileDto> showFileList(int repoId, int prId) {
        return prMapper.showFileList(repoId, prId);
    }

    @Override
    public FileDto showChangedCode(int repoId, int prId, int fileId) {
        return prMapper.showChangedCode(repoId, prId, fileId);
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
}
