package com.giteat.webHook.gitLab.service;

import com.giteat.api.GitLabApi;
import com.giteat.common.gitLab.mapper.GitLabTokenMapper;
import com.giteat.webHook.gitLab.entity.GitLabFileChangeEntity;
import com.giteat.webHook.gitLab.repository.GitLabFileChangeRepository;
import org.springframework.transaction.annotation.Transactional;
import com.giteat.webHook.gitLab.entity.GitLabCommitEntity;
import com.giteat.webHook.gitLab.entity.GitLabMergeRequestEntity;
import com.giteat.webHook.gitLab.entity.GitLabNoteEntity;
import com.giteat.webHook.gitLab.repository.GitLabCommitRepository;
import com.giteat.webHook.gitLab.repository.GitLabMergeRequestRepository;
import com.giteat.webHook.gitLab.repository.GitLabNoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@AllArgsConstructor
public class GitLabWebHookServiceImpl implements GitLabWebHookService {

    private final GitLabNoteRepository gitLabNoteRepository;
    private final GitLabMergeRequestRepository gitLabMergeRequestRepository;
    private final GitLabCommitRepository gitLabCommitRepository;
    private final GitLabApi gitLabApi;
    private final GitLabTokenMapper gitLabTokenMapper;
    private final GitLabFileChangeRepository gitLabFileChangeRepository;

    /**
     * pr 에 대한 event 처리하는 함수
     *
     * @param body
     */
    @Override
    @Transactional
    public void mergeRequestEvent(Map<String, Object> body) {

        // ----------------- pr 정보 저장 ------------
        GitLabMergeRequestEntity mergeRequestEntity = new GitLabMergeRequestEntity();
        Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
        Map<String, Object> userMap = (Map<String, Object>) body.get("user");
        Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("object");
        Map<String, Object> diffMap = (Map<String, Object>) body.get("diff_refs");

        mergeRequestEntity.setPrId((int) mergeRequestMap.get("id"));
        mergeRequestEntity.setRepoId((int) projectMap.get("id"));
        mergeRequestEntity.setTitle((String) mergeRequestMap.get("title"));
        mergeRequestEntity.setDescription((String) mergeRequestMap.get("description"));
        mergeRequestEntity.setCreateAt((String) mergeRequestMap.get("created_at"));
        mergeRequestEntity.setTargetBranch((String) mergeRequestMap.get("target_branch"));
        mergeRequestEntity.setSouceBranch((String) mergeRequestMap.get("source_branch"));
        mergeRequestEntity.setIsOpened("opened".equals(mergeRequestMap.get("state")) ? 1 : 0);

        // sha값들 암호화 해서 insert 해야함
        mergeRequestEntity.setBaseSha((String) diffMap.get("base_sha"));
        mergeRequestEntity.setHeadSha((String) diffMap.get("head_sha"));
        mergeRequestEntity.setStartSha((String) diffMap.get("start_sha"));

        gitLabMergeRequestRepository.save(mergeRequestEntity);


        // ---------- commit Date 저장 -----------
        String projectId = (String) projectMap.get("id");
        String prId = (String) mergeRequestMap.get("id");
        String userId = (String) userMap.get("id");

        String accessToken = gitLabTokenMapper.getAccessTokenById(userId);
        //accessToken이 유효한지 검사하는 로직 필요

        List<Map<String, Object>> gitCommitList = gitLabApi.getCommits(projectId, prId, userId);
        for (Map<String, Object> commit : gitCommitList) {
            GitLabCommitEntity commitEntity = new GitLabCommitEntity();
            commitEntity.setPrId((int) mergeRequestMap.get("id"));
            commitEntity.setRepositoryId((int) projectMap.get("id"));
            commitEntity.setCommitId((int) commit.get("id"));
            commitEntity.setContent((String) commit.get("message"));
            commitEntity.setCommitedAt((String) commit.get("createAt"));
            gitLabCommitRepository.save(commitEntity);

            //----------- diff 저장 ------------
            String commitId = (String) commit.get("id");
            List<Map<String, Object>> fileChangeList = gitLabApi.getChangeFiles(projectId, commitId, accessToken);

            for (Map<String, Object> fileChange : fileChangeList) {
                GitLabFileChangeEntity fileChangeEntity = new GitLabFileChangeEntity();
                //diff 관련해서 발급받은 지혜로 변경해서 저장해야함 newPath로 변경함
                //fileChangeEntity.setFileId();
                fileChangeEntity.setRepoId((int) projectMap.get("id"));
                fileChangeEntity.setPrId((int) mergeRequestMap.get("id"));
                fileChangeEntity.setCommitId(commitId);

                String fileName = (String) fileChange.get("new_path");
                int slashIndex = ((String) fileChange.get("new_path")).lastIndexOf("/");
                fileChangeEntity.setFileName(fileName.substring(slashIndex + 1));

                fileChangeEntity.setOldPath((String) fileChange.get("old_path"));
                fileChangeEntity.setNewPath((String) fileChange.get("new_path"));

                int fileStatus = 0;  // 기본값 설정

                if ((boolean) fileChange.get("renamed_file")) {
                    fileStatus = 1;  // 파일이 변경되었음
                } else if ((boolean) fileChange.get("deleted_file")) {
                    fileStatus = 2;  // 파일이 삭제됨
                } else if (fileChange.get("generated_file") != null && (boolean) fileChange.get("generated_file")) {
                    fileStatus = 3;  // 파일이 생성됨
                }
                fileChangeEntity.setFileStatus(fileStatus);

                gitLabFileChangeRepository.save(fileChangeEntity);
            }
        }


    }

    /**
     * 댓글에 대한 event 처리 함수
     *
     * @param body
     */
    @Override
    @Transactional
    public void noteEvent(Map<String, Object> body) {


    }
}


