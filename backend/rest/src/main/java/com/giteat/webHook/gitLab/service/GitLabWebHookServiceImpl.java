package com.giteat.webHook.gitLab.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.api.GitLabApi;
import com.giteat.common.gitLab.mapper.GitLabTokenMapper;
import com.giteat.common.util.SHA1Util;
import com.giteat.webHook.gitLab.entity.*;
import com.giteat.webHook.gitLab.repository.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.*;
//
//
@Service
@AllArgsConstructor
public class GitLabWebHookServiceImpl implements GitLabWebHookService {
    @Override
    public void mergeRequestEvent(Map<String, Object> body) {

    }

    @Override
    public void noteEvent(Map<String, Object> body) {

    }
}
    //
//    private final GitLabNoteRepository gitLabNoteRepository;
//    private final GitLabMergeRequestRepository gitLabMergeRequestRepository;
//    private final GitLabCommitRepository gitLabCommitRepository;
//    private final GitLabApi gitLabApi;
//    private final GitLabTokenMapper gitLabTokenMapper;
//    private final GitLabFileChangeRepository gitLabFileChangeRepository;
//    private final GitLabCommentRepository gitLabCommentRepository;
//    private final GitLabReplyRepository gitLabReplyRepository;
//    private final ObjectMapper objectMapper;
//
//    /**
//     * pr 에 대한 event 처리하는 함수
//     *
//     * @param body
//     */
//    @Override
//    @Transactional
//    public void mergeRequestEvent(Map<String, Object> body) {
//
//        // ----------------- pr 정보 저장 ------------
//        GitLabMergeRequestEntity mergeRequestEntity = new GitLabMergeRequestEntity();
//        Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
//        Map<String, Object> userMap = (Map<String, Object>) body.get("user");
//        Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("object");
//
//
//        mergeRequestEntity.setPrId((int) mergeRequestMap.get("id"));
//        mergeRequestEntity.setRepoId((int) projectMap.get("id"));
//        mergeRequestEntity.setTitle((String) mergeRequestMap.get("title"));
//        mergeRequestEntity.setDescription((String) mergeRequestMap.get("description"));
//        mergeRequestEntity.setCreateAt((String) mergeRequestMap.get("created_at"));
//        mergeRequestEntity.setTargetBranch((String) mergeRequestMap.get("target_branch"));
//        mergeRequestEntity.setSouceBranch((String) mergeRequestMap.get("source_branch"));
//        mergeRequestEntity.setIsOpened("opened".equals(mergeRequestMap.get("state")) ? 1 : 0);
//
//
//        String projectId = String.valueOf(projectMap.get("id"));
//        String prId = String.valueOf(mergeRequestMap.get("id"));
//        String userId = String.valueOf(userMap.get("id"));
//        String iid = String.valueOf(mergeRequestMap.get("iid"));
//
//        Map<String, Object> diffList = null;
//        Map<String, Object> diffMap = gitLabApi.getDiffRefs(projectId, iid, userId);
//
//    } catch(
//    IOException e)
//
//    {
//        System.err.println("Failed to parse JSON response: " + e.getMessage());
//        e.printStackTrace();  // 디버깅을 위해 예외 출력
//    }
//
//    String baseSha = (diffMap.get("base_sha"));
//    String headSha = String.valueOf(diffMap.get("head_sha"));
//    String startSha = String.valueOf(diffMap.get("start_sha"));
//        mergeRequestEntity.setBaseSha(baseSha);
//        mergeRequestEntity.setHeadSha(headSha);
//        mergeRequestEntity.setStartSha(startSha);
//
//        System.out.println(diffMap);
//    //gitLabMergeRequestRepository.save(mergeRequestEntity);
//
//
//    // ---------- commit Date 저장 -----------
////        String accessToken = gitLabTokenMapper.getAccessTokenById(userId);
//    //accessToken이 유효한지 검사하는 로직 필요
//
//    List<Map<String, Object>> gitCommitList = gitLabApi.getCommits(projectId, prId, userId);
//        for(
//    Map<String, Object> commit :gitCommitList)
//
//    {
//        GitLabCommitEntity commitEntity = new GitLabCommitEntity();
//        commitEntity.setPrId((int) mergeRequestMap.get("id"));
//        commitEntity.setRepositoryId((int) projectMap.get("id"));
//        commitEntity.setCommitId((String) commit.get("id"));
//        commitEntity.setContent((String) commit.get("message"));
//        commitEntity.setCommitedAt((String) commit.get("createAt"));
////            gitLabCommitRepository.save(commitEntity);
//
//        System.out.println(gitCommitList);
//
//
//        //----------- diff 저장 ------------
//        String commitId = (String) commit.get("id");
//        String accessToken = ":";
//        List<Map<String, Object>> fileChangeList = gitLabApi.getChangeFiles(projectId, commitId, accessToken);
//
//        for (Map<String, Object> fileChange : fileChangeList) {
//            GitLabFileChangeEntity fileChangeEntity = new GitLabFileChangeEntity();
//            //diff 관련해서 발급받은 지혜로 변경해서 저장해야함 newPath로 변경함
//            fileChangeEntity.setFileId(SHA1Util.encryptSHA1((String) fileChange.get("new_path")));
//            fileChangeEntity.setRepoId((int) projectMap.get("id"));
//            fileChangeEntity.setPrId((int) mergeRequestMap.get("id"));
//            fileChangeEntity.setCommitId(commitId);
//
//            String fileName = (String) fileChange.get("new_path");
//            int slashIndex = ((String) fileChange.get("new_path")).lastIndexOf("/");
//            fileChangeEntity.setFileName(fileName.substring(slashIndex + 1));
//
//            fileChangeEntity.setOldPath((String) fileChange.get("old_path"));
//            fileChangeEntity.setNewPath((String) fileChange.get("new_path"));
//
//            int fileStatus = 0;  // 기본값 설정
//
//            if ((boolean) fileChange.get("renamed_file")) {
//                fileStatus = 1;  // 파일이 변경되었음
//            } else if ((boolean) fileChange.get("deleted_file")) {
//                fileStatus = 2;  // 파일이 삭제됨
//            } else if (fileChange.get("generated_file") != null && (boolean) fileChange.get("generated_file")) {
//                fileStatus = 3;  // 파일이 생성됨
//            }
//            fileChangeEntity.setFileStatus(fileStatus);
//
//            gitLabFileChangeRepository.save(fileChangeEntity);
//        }
//    }
//
//
//}
//
///**
// * 댓글에 대한 event 처리 함수
// *
// * @param body
// */
//@Override
//@Transactional
//public void noteEvent(Map<String, Object> body) {
//
//    Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
//    Map<String, Object> userMap = (Map<String, Object>) body.get("user");
//    Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("object");
//    Map<String, Object> objectMap = (Map<String, Object>) body.get("object_attributes");
//
//    Integer parentId = (Integer) objectMap.get("parent_id");
//
//    if (parentId == null) { //부모글이 없으면 일반 댓글
//        GitLabCommentEntity commentEntity = new GitLabCommentEntity();
//        commentEntity.setCommentId((int) objectMap.get("id"));
//        commentEntity.setPrId((int) mergeRequestMap.get("id"));
//        commentEntity.setRepoId((int) projectMap.get("id"));
//        commentEntity.setContent((String) objectMap.get("note"));
//        commentEntity.setCommentType(0);
//        commentEntity.setUserId((int) userMap.get("id"));
//        commentEntity.setDisId((String) objectMap.get("discussion_id"));
//        commentEntity.setCreateAt((String) objectMap.get("created_at"));
//
//        String imageName = null;
//        if (objectMap.containsKey("attachement")) {
//            Map<String, Object> attachment = (Map<String, Object>) objectMap.get("attachment");
//            imageName = (String) attachment.get("image_name");
//            //String imageUrl = (String) attachement.get("url");
//        }
//        commentEntity.setImageName(imageName);
//        gitLabCommentRepository.save(commentEntity);
//
//    } else {  //대댓글
//        GitLabReplyEntity replyEntity = new GitLabReplyEntity();
//        replyEntity.setCommentId((int) objectMap.get("id"));
//        replyEntity.setRepoId((int) projectMap.get("id"));
//        replyEntity.setPrId((int) mergeRequestMap.get("id"));
//        replyEntity.setCommentId((int) objectMap.get("parent_id"));
//        replyEntity.setUserId((int) userMap.get("id"));
//        replyEntity.setDisId((String) objectMap.get("discussion_id"));
//        replyEntity.setContent((String) objectMap.get("note"));
//        replyEntity.setReplyType(0);
//        replyEntity.setCreateAt((Date) objectMap.get("created_at"));
//
//
//        String imageName = null;
//        if (objectMap.containsKey("attachement")) {
//            Map<String, Object> attachment = (Map<String, Object>) objectMap.get("attachment");
//            imageName = (String) attachment.get("image_name");
//            //String imageUrl = (String) attachement.get("url");
//        }
//        replyEntity.setImageName(imageName);
//        gitLabReplyRepository.save(replyEntity);
//    }
//
//}
//}
//
//
