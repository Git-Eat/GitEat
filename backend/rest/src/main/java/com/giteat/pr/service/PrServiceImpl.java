package com.giteat.pr.service;

import com.giteat.pr.dto.*;
import com.giteat.pr.mapper.PrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PrServiceImpl")
public class PrServiceImpl implements PrService{

    //private final PrMapper prMapper;


    @Override
    public List<PrDto> getPrList(int repoId) {
        return List.of();
    }

    @Override
    public PrDto getPrById(int repoId, int prId) {
        return null;
    }

    @Override
    public List<CommitDto> getCommitList(int repoId, int prId) {
        return List.of();
    }

    @Override
    public CommitDto getCommitById(int repoId, int prId, int commitId) {
        return null;
    }

    @Override
    public List<CommentDto> getCommentList(int repoId, int prId) {
        return List.of();
    }

    @Override
    public int insertComment(CommentDto commentDto) {
        return 0;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        return null;
    }

    @Override
    public int deleteComment(int repoId, int prId, int commentId) {
        return 0;
    }

    @Override
    public List<ReplyDto> showReply(int repoId, int prId, int commentId) {
        return List.of();
    }

    @Override
    public int insertReply(ReplyDto replyDto) {
        return 0;
    }

    @Override
    public ReplyDto updateReply(ReplyDto replyDto) {
        return null;
    }

    @Override
    public int deleteReply(int replyId) {
        return 0;
    }

    @Override
    public List<FileDto> showFileList(int repoId, int prId) {
        return List.of();
    }

    @Override
    public FileDto showChangedCode(int repoId, int prId, int fileId) {
        return null;
    }
}
