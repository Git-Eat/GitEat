package com.giteat.webHook.gitLab.service;

import com.giteat.api.GitLabApi;
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
public class GitLabWebHookServiceImpl implements GitLabWebHookService{

    private final GitLabNoteRepository gitLabNoteRepository;
    private final GitLabMergeRequestRepository gitLabMergeRequestRepository;
    private final GitLabApi gitLabApi;
    /**
     * pr 에 대한 event 처리하는 함수
     * @param body
     */
    @Override
    public void mergeRequestEvent(Map<String, Object> body) {
        GitLabMergeRequestEntity mergeRequestEntity = new GitLabMergeRequestEntity();
        //데이터 정제하는 코드 추가 필요함

        Map<String , Object> projectMap = (Map<String , Object>) body.get("project");
        Map<String , Object> userMap = (Map<String , Object>)body.get("user");
        Map<String , Object> mergeRequestMap = (Map<String , Object>) body.get("object");

        int projectId = (int)projectMap.get("id");
        int prId = (int)mergeRequestMap.get("id");

        mergeRequestEntity.setPrId((int)mergeRequestMap.get("id"));
        mergeRequestEntity.setRepoId((int)projectMap.get("id"));
        mergeRequestEntity.setTitle((String)mergeRequestMap.get("title"));
        mergeRequestEntity.setDescription((String)mergeRequestMap.get("description"));
        mergeRequestEntity.setCreateAt((String) mergeRequestMap.get("created_at"));
        mergeRequestEntity.setTargetBranch((String) mergeRequestMap.get("target_branch"));
        mergeRequestEntity.setSouceBranch((String) mergeRequestMap.get("source_branch"));
        mergeRequestEntity.setIsOpened("opened".equals(mergeRequestMap.get("state")) ? 1 : 0);

        gitLabMergeRequestRepository.save(mergeRequestEntity);

        //사용자 정보를 기반으로 accessToken을 가져온다.

        // gitLabApi.getFiles(projectId , prId);를 호출한다 저장한다.

        //userId 값을 가져와서 id를 가져와서 accessToken을 꺼낸다.

        // accessToken이 유효한지 검사한다.

        // accessToken이 유효할 경우 로직을 실행하고 유효하지 않을 경우 refresh 토큰으로 access를 발급받는다.


    }

    /**
     * 댓글에 대한 event 처리 함수
     * @param body
     */
    @Override
    @Transactional
    public void noteEvent(Map<String, Object> body) {
        // 1. Repository ID 추출
        int repoId = (int) ((Map<String, Object>) body.get("repository")).get("id");

        Integer prId = null;
        if (body.containsKey("merge_request")) {
            Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("merge_request");
            prId = (int) mergeRequestMap.get("id");
        }

        if (body.containsKey("object_attributes")) {
            Map<String, Object> objectAttributes = (Map<String, Object>) body.get("object_attributes");

            GitLabNoteEntity noteEntity = new GitLabNoteEntity();

            // 필요한 값 매핑
            noteEntity.setPrId(prId != null ? prId : 0);  // PR ID가 없을 경우 기본값 0
            noteEntity.setRepoId(repoId);
            noteEntity.setContent((String) objectAttributes.get("note")); // 댓글 내용
            noteEntity.setCommentType((Integer) objectAttributes.get("type"));  // 댓글의 타입 (예시로 Integer 처리)
            noteEntity.setUserId((Integer) objectAttributes.get("author_id"));  // 작성자 ID
            noteEntity.setDisId((String) objectAttributes.get("dis_id"));  // 댓글의 디스 ID (예시로 처리)
            noteEntity.setImageName((String) objectAttributes.get("image_name"));  // 이미지 이름 (필요시 이미지 처리)
            noteEntity.setCreateAt((String) objectAttributes.get("created_at"));  // 댓글 작성 시간
            noteEntity.setCommentTyple((String) objectAttributes.get("type"));  // 주어진 'comment_typle'에 대응 (타입)
            noteEntity.setDepth(0);  // 댓글의 깊이는 기본값 0으로 설정 (필요시 수정 가능)

            // 4. 데이터 저장
            gitLabNoteRepository.save(noteEntity);
        }
    }
}


