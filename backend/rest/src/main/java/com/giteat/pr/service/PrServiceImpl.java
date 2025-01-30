package com.giteat.pr.service;

import com.giteat.pr.dto.*;
import com.giteat.pr.entity.CommitEntity;
import com.giteat.pr.entity.PrEntity;
import com.giteat.pr.mapper.PrMapper;
import com.giteat.pr.repository.PrRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PrServiceImpl")
public class PrServiceImpl implements PrService{

    private final PrMapper prMapper;
    private final PrRepository prRepository;

    public PrServiceImpl(PrMapper prMapper, PrRepository prRepository){
        this.prMapper = prMapper;
        this.prRepository = prRepository;
    }


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
    public int insertComment(CommentDto commentDto) {
        return prMapper.insertComment(commentDto);
    }

    @Override
    public int updateComment(CommentDto commentDto) {
        return prMapper.updateComment(commentDto);
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
    public int insertReply(ReplyDto replyDto) {
        return prMapper.insertReply(replyDto);
    }

    @Override
    public int updateReply(ReplyDto replyDto) {
        return prMapper.updateReply(replyDto);
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
}
