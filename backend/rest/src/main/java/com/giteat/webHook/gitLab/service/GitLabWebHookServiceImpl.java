package com.giteat.webHook.gitLab.service;

import com.giteat.api.LabApi;
import com.giteat.common.util.SHA1Util;
import com.giteat.pr.dto.PrDto;
import com.giteat.repo.entity.*;
import com.giteat.repo.repository.*;
import com.giteat.webHook.gitLab.dto.CommentTempDto;
import com.giteat.webHook.gitLab.dto.MergeRequestTempDto;
import com.giteat.webHook.gitLab.mapper.GitLabWebHookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@RequiredArgsConstructor
public class GitLabWebHookServiceImpl implements GitLabWebHookService {

    private final LabApi gitLabApi;
    private final MergeRequestRepository mergeRequestRepository;
    private final CommitRepository commitRepository;
    private final FileChangeRepository fileChangeRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final GitLabWebHookMapper gitLabWebHookMapper;
    /**
     * pr 에 대한 event 처리하는 함수
     *
     * @param body
     */
    @Override
//    @Transactional
    public void mergeRequestEvent(Map<String, Object> body) {

        Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
        Map<String, Object> userMap = (Map<String, Object>) body.get("user");
        Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("object_attributes");

        System.out.println("projectMap : " + projectMap);
        System.out.println(" ");
        System.out.println("userMap: " + userMap);
        System.out.println(" ");
        System.out.println("prMap : " + mergeRequestMap);
        System.out.println(" ");

        int repoId = (int)projectMap.get("id");
        int prId = (int) mergeRequestMap.get("iid");
        int userId = (int) userMap.get("id");

        System.out.println("MR repoId:" + repoId);
        System.out.println("PR prId : " + prId);
        System.out.println("USER ID : " + userId);
        System.out.println("==================================================");
        int prTableCheck = gitLabWebHookMapper.prTableCheck(repoId , prId);
        System.out.println("PR CHECK 값 : " + prTableCheck);
        if(prTableCheck==1){        //데이터 있음
            MergeRequestTempDto mrTempDto = new MergeRequestTempDto();
            mrTempDto.setRepoId((int) projectMap.get("id"));
            mrTempDto.setPrId((int) mergeRequestMap.get("iid"));
            mrTempDto.setUserId((int) userMap.get("id"));
            mrTempDto.setTempStatus(0);
            gitLabWebHookMapper.updateMergeRequestStatus(mrTempDto);
        }else{
            MergeRequestEntity mergeRequestEntity = new MergeRequestEntity();
            MergeRequestId mrId = new MergeRequestId(prId, repoId);

            mergeRequestEntity.setId(mrId);
            mergeRequestEntity.setTitle((String) mergeRequestMap.get("title"));
            mergeRequestEntity.setDescription((String) mergeRequestMap.get("description"));
            mergeRequestEntity.setUserId((int) userMap.get("id"));
            mergeRequestEntity.setCreateAt((String) mergeRequestMap.get("created_at"));
            String isOpend = (String)mergeRequestMap.get("state");
            int isOpen = 0;
            if(isOpend.equals("opened")){
                isOpen = 1;
            }else if(isOpend.equals("closed")){
                isOpen = 2;
            }else if(isOpend.equals("merged")){
                isOpen = 3;
            }
            mergeRequestEntity.setIsOpened(isOpen);
            mergeRequestEntity.setTargetBranch((String) mergeRequestMap.get("target_branch"));
            mergeRequestEntity.setSourceBranch((String) mergeRequestMap.get("source_branch"));
            mergeRequestEntity.setIsOpened("opened".equals(mergeRequestMap.get("state")) ? 1 : 0);
            mergeRequestEntity.setPrType(1);
            mergeRequestEntity.setUserName((String)userMap.get("name"));
            mergeRequestEntity.setUserProfile((String)userMap.get("avatar_url"));

            mergeRequestRepository.save(mergeRequestEntity);
            System.out.println("저장한값 : " + mrId.getPrId() + " : " + mrId.getRepoId());


            //pr temp 테이블에 데이터 넣기
            MergeRequestTempDto mrTempDto = new MergeRequestTempDto();
            mrTempDto.setRepoId(repoId);
            mrTempDto.setPrId(prId);
            mrTempDto.setUserId(userId);
            mrTempDto.setTempStatus(0);
            System.out.println("tempDto : " + mrTempDto);
            gitLabWebHookMapper.insertMergeRequestTemp(mrTempDto);
        }
    }


    /**
     * webHook 이 후 사용자 요청마다 데이터를 추가하거나 검사하는 함수
     * @param accessToken
     */
    @Override
//    @Transactional
    public void addMergeRequestData(String accessToken) {
        List<MergeRequestTempDto> prTempList = gitLabWebHookMapper.getPrTemp(accessToken);
        System.out.println("prTempList size : " + prTempList.size());
        for (MergeRequestTempDto prTempDto : prTempList) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@가져온 데이터 : " + prTempDto);

            String projectId = String.valueOf(prTempDto.getRepoId());
            String prId = String.valueOf(prTempDto.getPrId());
            String userId = String.valueOf(prTempDto.getUserId());


            // sha 관련 데이터 넣기
            PrDto prDto = new PrDto();

            Map<String, Object> diffMap = gitLabApi.getDiffRefs(projectId, prId, accessToken);
            Map<String, Object> diffRefsMap = (Map<String, Object>) diffMap.get("diff_refs");

            String baseSha = String.valueOf(diffRefsMap.get("base_sha"));
            String headSha = String.valueOf(diffRefsMap.get("head_sha"));
            String startSha = String.valueOf(diffRefsMap.get("start_sha"));
            prDto.setRepoId(Integer.parseInt(projectId));
            prDto.setPrId(Integer.parseInt(prId));
            prDto.setBaseSha(baseSha);
            prDto.setHeadSha(headSha);
            prDto.setStartSha(startSha);
            prDto.setPrType(1);

            // pr의 값을 update하는 구문 작성
            gitLabWebHookMapper.updateMergeRequestData(prDto);


            // ------------ commit 저장하는 함수 -----------------
            List<Map<String, Object>> gitCommitList = gitLabApi.getCommits(projectId, Integer.parseInt(prId), accessToken);
            for (Map<String, Object> commit : gitCommitList) {
                System.out.println("COMMIT DATA : " + commit);
                CommitEntity commitEntity = new CommitEntity();
                CommitId commitId = new CommitId((String) commit.get("id"), Integer.valueOf(projectId), Integer.valueOf(prId));
                commitEntity.setId(commitId);
                commitEntity.setContent((String) commit.get("message"));
                commitEntity.setCommitedAt((String) commit.get("committed_date"));
                commitRepository.save(commitEntity);



                // id값으로 accessToken 가져오는 로직이 필요하다.
                // userId 를 사용해서 사용

                for (int prPageNation = 1; prPageNation <= 20; prPageNation++) {
                    List<Map<String, Object>> fileChangeList = gitLabApi.getFilesByPr(projectId, Integer.parseInt(prId), prPageNation, accessToken);
                    if (fileChangeList.isEmpty()) break; // 배열이 비어있다면(받아온 값이 없다면) for문 탈출

                    for (Map<String, Object> fileChange : fileChangeList) {
                        FileChangeEntity fileChangeEntity = new FileChangeEntity();
                        FileChangeId fileChangeId = new FileChangeId(SHA1Util.encryptSHA1((String) fileChange.get("new_path")),
                                Integer.parseInt(projectId), Integer.parseInt(prId));

                        fileChangeEntity.setId(fileChangeId);
                        String fileName = (String) fileChange.get("new_path");
                        int slashIndex = ((String) fileChange.get("new_path")).lastIndexOf("/");
                        if (slashIndex != -1) {
                            fileChangeEntity.setFileName(fileName.substring(slashIndex + 1));
                        } else {
                            fileChangeEntity.setFileName((String) fileChange.get("new_path"));
                        }

                        fileChangeEntity.setOldPath((String) fileChange.get("old_path"));
                        fileChangeEntity.setNewPath((String) fileChange.get("new_path"));

                        int fileStatus = 0;  // 기본값 설정

                        // 1. add , 2. update, 3. delete
                        if ((boolean) fileChange.get("new_file")) {
                            fileStatus = 1;
                        } else if ((boolean) fileChange.get("renamed_file")) {
                            fileStatus = 2;
                        } else if ((boolean) fileChange.get("deleted_file")) {
                            fileStatus = 3;
                        } else if ((!(boolean) fileChange.get("new_file") && !(boolean) fileChange.get("renamed_file") && !(boolean) fileChange.get("deleted_file"))) {
                            fileStatus = 2;
                        }
                        fileChangeEntity.setFileStatus(fileStatus);

                        fileChangeRepository.save(fileChangeEntity);
                    }
                }
            }
            // pr의 status를 update하는 구문
            System.out.println("prId : " + prTempDto.getPrId());
            System.out.println("repoId : " + prTempDto.getRepoId());
            gitLabWebHookMapper.updateMergeRequestStatus(prTempDto);
        }
    }




    /**
     * 댓글에 대한 event 처리 함수
     *
     * @param body
     */
    @Override
    public void noteEvent(Map<String, Object> body) {

        Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
        Map<String, Object> userMap = (Map<String, Object>) body.get("user");
        Map<String, Object> commentMap = (Map<String, Object>) body.get("object_attributes");
        Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("merge_request");

        int userId = (int)userMap.get("id");
        int prId = (int)mergeRequestMap.get("iid");
        int repoId = (int)projectMap.get("id");
        System.out.println("note userId : " + userId);
        System.out.println("note prId : " + prId);
        System.out.println("note repoId : " + repoId);

        CommentTempDto commentTempDto = new CommentTempDto();
        commentTempDto.setPrId(prId);
        commentTempDto.setRepoId(repoId);
        commentTempDto.setTempStatus(0);
        gitLabWebHookMapper.insertCommentTemp(commentTempDto);
    }


    @Override
    public void addNoteData(String accessToken) {
        System.out.println("addNoteData 실행 !!!!");
        List<CommentTempDto> commentTempList = gitLabWebHookMapper.getCommentList(accessToken);
        System.out.println("list 사이즈 : " + commentTempList.size());
        for (CommentTempDto comments : commentTempList) {
            int repoId = comments.getRepoId();
            int prId = comments.getPrId();

            // ---------- Comment 가져오기 ---------- //
            List<Map<String, Object>> CommentList = gitLabApi.getDiscussions(String.valueOf(repoId), prId, accessToken);
            for (Map<String, Object> commentResponse : CommentList) {
                List<Map<String, Object>> notes = (List<Map<String, Object>>) commentResponse.get("notes");

                // 첫번째 note는 Comment로 저장
                Map<String, Object> firstNote = notes.get(0);
                if ((boolean) notes.get(0).get("system")) continue; // system이 쓴 댓글이면 continue
                CommentEntity comment = new CommentEntity();
                CommentId commentId = new CommentId((int) firstNote.get("id"), prId, repoId);
                Map<String, Object> commentAuthor = (Map<String, Object>) notes.get(0).get("author");

                comment.setId(commentId);
                comment.setContent((String) firstNote.get("body"));
                comment.setCommentType(0); // type 알아본 후 재설정하기
                comment.setUserId((int) commentAuthor.get("id"));
                comment.setDisId((String) commentResponse.get("id"));
                comment.setCreateAt((String) firstNote.get("updated_at"));

                if (firstNote.get("position") != null) {

                    Map<String, Object> position = (Map<String, Object>) firstNote.get("position");
                    if (position.get("new_line") != null) comment.setNewLine((int) position.get("new_line"));
                    if (position.get("old_line") != null) comment.setOldLine((int) position.get("old_line"));

                    Optional<MergeRequestEntity> optionalMr = mergeRequestRepository.findByRepoIdAndPrId(repoId, prId);

                    if (optionalMr.isPresent()) {
                        // MR 정보 업데이트
                        MergeRequestEntity existingMr = optionalMr.get();
                        existingMr.setBaseSha((String) position.get("base_sha"));
                        existingMr.setHeadSha((String) position.get("head_sha"));
                        existingMr.setStartSha((String) position.get("start_sha"));
                        mergeRequestRepository.save(existingMr); // 업데이트
                    }

                    Map<String, Object> lineRange = (Map<String, Object>) position.get("line_range");
                    if (lineRange != null) {
                        Map<String, Object> start = (Map<String, Object>) lineRange.get("start");
                        Map<String, Object> end = (Map<String, Object>) lineRange.get("end");
                        String CommentFileId = (String) start.get("line_code");
                        String extractedFileId = CommentFileId.split("_")[0];
                        comment.setFileId(extractedFileId); // fileId 추출해서 저장
                        if (start.get("new_line") != null) comment.setNewStartLine((int) start.get("new_line"));
                        if (end.get("new_line") != null) comment.setNewEndLine((int) end.get("new_line"));
                        if (start.get("old_line") != null) comment.setOldStartLine((int) start.get("old_line"));
                        if (end.get("old_line") != null) comment.setOldEndLine((int) end.get("old_line"));
                    }
                }
                commentRepository.save(comment);
            }


        }
    }
}