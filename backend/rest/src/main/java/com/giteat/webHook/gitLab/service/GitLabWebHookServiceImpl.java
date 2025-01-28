package com.giteat.webHook.gitLab.service;

import com.giteat.webHook.gitLab.entity.GitLabCommitEntity;
import com.giteat.webHook.gitLab.entity.GitLabMergeRequestEntity;
import com.giteat.webHook.gitLab.entity.GitLabNoteEntity;
import com.giteat.webHook.gitLab.repository.GitLabWebHookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GitLabWebHookServiceImpl implements GitLabWebHookService{

    private final GitLabWebHookRepository gitLabRepository;

    @Autowired
    public GitLabWebHookServiceImpl(GitLabWebHookRepository gitLabWebHookRepository){
        this.gitLabRepository = gitLabWebHookRepository;
    }

    //TODO : 현재 데이터를 받아서 저장하는 로직만 설정해놓은 상태이다. 이 상태에서 추가 더 필요 데이터가 있을 경우 처리해서 저장 해야한다.


    /**
     * commit에 대한 event 처리
     * @param body
     */
    @Override
    public void commitEvent(Map<String, Object> body) {
        GitLabCommitEntity commitEntity = new GitLabCommitEntity();
        //데이터 정제하는 코드 추가 필요함
        gitLabRepository.save(commitEntity);
    }

    /**
     * pr 에 대한 event 처리하는 함수
     * @param body
     */
    @Override
    public void mergeRequestEvent(Map<String, Object> body) {
        GitLabMergeRequestEntity mergeRequestEntity = new GitLabMergeRequestEntity();
        //데이터 정제하는 코드 추가 필요함
        gitLabRepository.save(mergeRequestEntity);
    }

    /**
     * 댓글에 대한 event 처리 함수
     * @param body
     */
    @Override
    public void noteEvent(Map<String, Object> body) {
        GitLabNoteEntity noteEntity = new GitLabNoteEntity();
        //데이터 정제하는 코드 추가 필요함
        gitLabRepository.save(noteEntity);
    }
}
