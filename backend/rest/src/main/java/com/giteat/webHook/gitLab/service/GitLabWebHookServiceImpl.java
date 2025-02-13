package com.giteat.webHook.gitLab.service;

import com.giteat.api.LabApi;
import com.giteat.common.gitLab.mapper.GitLabTokenMapper;
import com.giteat.common.util.SHA1Util;
import com.giteat.pr.dto.PrDto;
import com.giteat.repo.entity.*;
import com.giteat.repo.repository.*;
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
    private final GitLabTokenMapper gitLabTokenMapper;
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
    @Transactional
    public void mergeRequestEvent(Map<String, Object> body) {

        // ----------------- pr 정보 저장 ------------
        System.out.println("service BODYO  :" + body);

        MergeRequestEntity mergeRequestEntity = new MergeRequestEntity();

        Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
        Map<String, Object> userMap = (Map<String, Object>) body.get("user");
        Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("object_attributes");

        MergeRequestId mrId = new MergeRequestId((int) mergeRequestMap.get("id"), (int) projectMap.get("id"));

        mergeRequestEntity.setId(mrId);
        mergeRequestEntity.setTitle((String) mergeRequestMap.get("title"));
        mergeRequestEntity.setDescription((String) mergeRequestMap.get("description"));
        mergeRequestEntity.setCreateAt((String) mergeRequestMap.get("created_at"));
        mergeRequestEntity.setTargetBranch((String) mergeRequestMap.get("target_branch"));
        mergeRequestEntity.setSourceBranch((String) mergeRequestMap.get("source_branch"));
        mergeRequestEntity.setIsOpened("opened".equals(mergeRequestMap.get("state")) ? 1 : 0);
        mergeRequestEntity.setPrType(1);

        mergeRequestRepository.save(mergeRequestEntity);
        System.out.println("entity : " + mergeRequestEntity);
        //pr temp 테이블에 데이터 넣기
        MergeRequestTempDto mrTempDto = new MergeRequestTempDto();
        mrTempDto.setRepoId((int) projectMap.get("id"));
        mrTempDto.setPrId((int) mergeRequestMap.get("id"));
        mrTempDto.setPrIid((int) mergeRequestMap.get("iid"));
        mrTempDto.setTempStatus(0);
        System.out.println("tempDto : " + mrTempDto);
        gitLabWebHookMapper.insertMergeRequestTemp(mrTempDto);
    }


    /**
     * webHook 이 후 사용자 요청마다 데이터를 추가하거나 검사하는 함수
     * @param accessToken
     */
    @Override
    @Transactional
    public void addMergeRequestData(String accessToken) {
        List<MergeRequestTempDto> prTempList = gitLabWebHookMapper.getPrTemp(accessToken);
        for (MergeRequestTempDto prTempDto : prTempList) {
            String projectId = String.valueOf(prTempDto.getRepoId());
            String prId = String.valueOf(prTempDto.getPrId());
            String userId = String.valueOf(prTempDto.getUserId());
            String iid = String.valueOf(prTempDto.getPrIid());

            // sha 관련 데이터 넣기
            PrDto prDto = new PrDto();

            Map<String, Object> diffMap = gitLabApi.getDiffRefs(projectId, prId, accessToken);
            Map<String, Object> diffRefsMap = (Map<String, Object>) diffMap.get("diff_refs");

            String baseSha = String.valueOf(diffRefsMap.get("base_sha"));
            String headSha = String.valueOf(diffRefsMap.get("head_sha"));
            String startSha = String.valueOf(diffRefsMap.get("start_sha"));
            prDto.setBaseSha(baseSha);
            prDto.setHeadSha(headSha);
            prDto.setStartSha(startSha);
            prDto.setPrType(1);

            // pr의 값을 update하는 구문 작성
            gitLabWebHookMapper.updateMergeRequestData(prDto);
            // pr의 status를 update하는 구문
            gitLabWebHookMapper.updateMergeRequestStatus(prDto);

            // ------------ commit 저장하는 함수 -----------------
            List<Map<String, Object>> gitCommitList = gitLabApi.getWebHookCommit(projectId, prId, accessToken);
            for (Map<String, Object> commit : gitCommitList) {

                CommitEntity commitEntity = new CommitEntity();
                CommitId commitId = new CommitId((String) commit.get("id"), Integer.valueOf(projectId), Integer.valueOf(prId));
                commitEntity.setId(commitId);
                commitEntity.setContent((String) commit.get("message"));
                commitEntity.setCommitedAt((String) commit.get("committed_date"));
                commitRepository.save(commitEntity);



                // id값으로 accessToken 가져오는 로직이 필요하다.
                // userId 를 사용해서 사용

                for (int prPageNation = 1; prPageNation <= 20; prPageNation++) {
                    List<Map<String, Object>> fileChangeList = gitLabApi.getFilesByPr(projectId, Integer.parseInt(iid), prPageNation, accessToken);
                    if (fileChangeList.isEmpty()) break; // 배열이 비어있다면(받아온 값이 없다면) for문 탈출

                    for (Map<String, Object> fileChange : fileChangeList) {
                        FileChangeEntity fileChangeEntity = new FileChangeEntity();
                        FileChangeId fileChangeId = new FileChangeId(SHA1Util.encryptSHA1((String) fileChange.get("new_path")),
                                Integer.parseInt(projectId), Integer.parseInt(iid));

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
        }
    }




    /**
     * 댓글에 대한 event 처리 함수
     *
     * @param body
     */
    @Transactional
    @Override
    public void noteEvent(Map<String, Object> body) {
        Map<String, Object> projectMap = (Map<String, Object>) body.get("project");
        Map<String, Object> userMap = (Map<String, Object>) body.get("user");
        Map<String, Object> commentMap = (Map<String, Object>) body.get("object_attributes");
        Map<String, Object> mergeRequestMap = (Map<String, Object>) body.get("merge_request");

        //db에 값이 있는지 확인한다.
        int commentId = (int) commentMap.get("id");
        int userId = (int) userMap.get("id");
        int prId = (int) mergeRequestMap.get("id");
        int repoId = (int) projectMap.get("id");
        int prIid = (int) mergeRequestMap.get("iid");
        int commentCnt = gitLabWebHookMapper.getReplyCnt(commentId);
        String accessToken = gitLabTokenMapper.getAccessTokenById(userId);

        if(commentCnt==0){  // 0일경우 댓글임
            CommentEntity commentEntity = new CommentEntity();
            CommentId entityCommentId = new CommentId(commentId , prId , repoId);
            commentEntity.setId(entityCommentId);
            commentEntity.setContent((String) commentMap.get("note"));
            commentEntity.setCommentType(0);
            commentEntity.setUserId(userId);

            List<Map<String, Object>> commentList = gitLabApi.getDiscussions(String.valueOf(repoId), prIid, accessToken);
            Map<String , Object> disMap = commentList.get(0);

            commentEntity.setDisId((String) disMap.get("id"));
            commentEntity.setCreateAt((String) commentMap.get("created_at"));

            if(disMap.get("position") != null){
                Map<String, Object> position = (Map<String, Object>) disMap.get("position");
                if(position.get("new_line") !=null) commentEntity.setNewLine((int) position.get("new_line"));
                if(position.get("old_line") !=null) commentEntity.setOldLine((int) position.get("old_line"));

                Optional<MergeRequestEntity> optionalMr = mergeRequestRepository.findById_PrId(prIid);

                Map<String, Object> lineRange = (Map<String, Object>) position.get("line_range");
                if(lineRange != null){
                    Map<String, Object> start = (Map<String, Object>) lineRange.get("start");
                    Map<String, Object> end = (Map<String, Object>) lineRange.get("end");
                    if(start.get("new_line") !=null)  commentEntity.setNewStartLine((int) start.get("new_line"));
                    if(end.get("new_line") !=null) commentEntity.setNewEndLine((int) end.get("new_line"));
                    if(start.get("old_line") !=null) commentEntity.setOldStartLine((int) start.get("old_line"));
                    if(end.get("old_line") !=null) commentEntity.setOldEndLine((int) end.get("old_line"));
                }
            }
            commentRepository.save(commentEntity);
            System.out.println("댓글 저장 완료");
        }else{// 대댓글임
            ReplyEntity replyEntity = new ReplyEntity();

            List<Map<String, Object>> commentList = gitLabApi.getDiscussions(String.valueOf(repoId), prIid, accessToken);
            Map<String , Object> disMap = commentList.get(0);

            Map<String , Object> mapperMap = new HashMap();
            mapperMap.put("disId" , disMap.get("id"));
            mapperMap.put("prId" , prId);
            mapperMap.put("repoId" , repoId);

            int replyCommentId = gitLabWebHookMapper.getCommentId(mapperMap);   //테이블 comment_id
            int reCommentId = commentId;                                        //테이블 re_comment_id
            ReplyId replyId = new ReplyId(reCommentId , replyCommentId , prId , repoId);
            replyEntity.setId(replyId);
            replyEntity.setUserId(userId);
            replyEntity.setDisId((String) disMap.get("id"));
            replyEntity.setContent((String) commentMap.get("note"));
            replyEntity.setReCommentType(1);
            replyEntity.setCreateAt((String) commentMap.get("created_at"));

            replyRepository.save(replyEntity);
            System.out.println("대댓글 저장완료");
        }
    }



    @Override
    public void addNoteData(String accessToken) {

    }
}
