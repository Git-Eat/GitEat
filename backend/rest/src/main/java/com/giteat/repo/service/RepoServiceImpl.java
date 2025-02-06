package com.giteat.repo.service;

import com.giteat.api.LabApi;
import com.giteat.common.util.SHA1Util;
import com.giteat.repo.entity.*;
import com.giteat.repo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("RepoServiceImpl")
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService{

    private final RepoRepository repoRepository;
    private final MergeRequestRepository mergeRequestRepository;
    private final CommitRepository commitRepository;
    private final FileChangeRepository fileChangeRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final LabApi gitLabApi;

    @Override
    public List<RepositoryEntity> getRepoList() {
        return repoRepository.getRepoList();
    }

    @Override
    public RepositoryEntity findByRepoId(int repoId) {
        return repoRepository.findByRepoId(repoId);
    }

    @Override
    public RepositoryEntity insertRepo(int repoId) {
        RepositoryEntity repo = new RepositoryEntity();
        repo.setRepoId(repoId);
        return repoRepository.save(repo);
    }

    @Override
    public int deleteRepo(int repoId) {
        return repoRepository.deleteRepo(repoId);
    }

    @Override
    public int saveRepositoryData(String accessToken , String projectId){
        accessToken = "UATEgVcVTSsLn7PWao6c";
        int repoId = Integer.parseInt(projectId);

        // ---------- repo 정보부터 가져오기 ----------- //
        RepositoryEntity repository = new RepositoryEntity();
        Map<String, Object> repositoryResponse = gitLabApi.getRepository(projectId,accessToken);
        repository.setRepoId((Integer) repositoryResponse.get("id"));
        repository.setName((String) repositoryResponse.get("name"));
        repository.setDescription((String) repositoryResponse.get("description"));
        repository.setGitlabUrl((String) repositoryResponse.get("web_url"));
        repository.setCreateAt((LocalDateTime) repositoryResponse.get("created_at"));
        repoRepository.save(repository);
        
        // ---------- MR 정보 가져오기 ---------- //
        Map<String, Object> newMrResponse = gitLabApi.getMergeRequests(projectId, accessToken); // 가장 최상단 MR 번호 가져오기
        if(newMrResponse.isEmpty()) return 1; // MR 없으면 아래 다 건너뛰어

        Integer newMR = (Integer) newMrResponse.get("iid");
        for(int iid = 1; iid <= newMR; iid++){
            Map<String, Object> mrResponse = gitLabApi.getMergeRequestsById(projectId,iid,accessToken);
            Map<String, Object> diffRefsMap = (Map<String, Object>) mrResponse.get("diff_refs");
            MergeRequestEntity mr = new MergeRequestEntity();
            mr.setPrId((Integer) mrResponse.get("iid"));
            mr.setRepoId(Integer.parseInt(projectId));
            mr.setTitle((String) mrResponse.get("title"));
            mr.setDescription((String) mrResponse.get("description"));
            mr.setCreateAt((String) mrResponse.get("created_at"));
            mr.setIsOpened("opened".equals(mrResponse.get("state")) ? 1 : 0); // 나중에 수정해야될수도
            mr.setSourceBranch((String) mrResponse.get("source_branch"));
            mr.setTargetBranch((String) mrResponse.get("target_branch"));
            mr.setBaseSha((String) diffRefsMap.get("base_sha"));
            mr.setHeadSha((String) diffRefsMap.get("head_sha"));
            mr.setStartSha((String) diffRefsMap.get("start_sha"));
            //userId 어떻게 해야함 ?
            mergeRequestRepository.save(mr);

            // ---------- Commit 정보 가져오기 ---------- //
            List<Map<String, Object>> CommitList = gitLabApi.getCommits(projectId,iid, accessToken);
            for(Map<String, Object> commitResponse : CommitList){
                CommitEntity commitEntity = new CommitEntity();
                commitEntity.setRepositoryId((Integer) repositoryResponse.get("id"));
                commitEntity.setPrId((Integer) mrResponse.get("iid"));
                commitEntity.setCommitId((String) commitResponse.get("id"));
                commitEntity.setContent((String) commitResponse.get("message"));
                commitEntity.setCommitedAt((String) commitResponse.get("committed_date"));
                commitRepository.save(commitEntity);

                // ---------- FileChange 가져오기 ---------- //
                String commitId = (String) commitResponse.get("id");
                List<Map<String, Object>> fileChangeList = gitLabApi.getFilesByCommit(projectId, commitId, accessToken);

                for (Map<String, Object> fileChange : fileChangeList) {
                    FileChangeEntity fileChangeEntity = new FileChangeEntity();

                    fileChangeEntity.setFileId(SHA1Util.encryptSHA1((String) fileChange.get("new_path")));
                    fileChangeEntity.setRepoId((int) repositoryResponse.get("id"));
                    fileChangeEntity.setPrId((int) mrResponse.get("id"));
                    fileChangeEntity.setCommitId(commitId);

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
                    System.out.println((boolean) fileChange.get("renamed_file"));

                    //1. add , 2. update, 3. delete
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

            // ---------- Comment 가져오기 ---------- //
            List<Map<String, Object>> CommentList = gitLabApi.getDiscussions(projectId, iid, accessToken);
            for(Map<String, Object> commentResponse : CommentList){
                if(!(boolean) commentResponse.get("individual_note")) continue; // individual_note값이 true일때만 DB에 저장

                List<Map<String, Object>> notes = (List<Map<String, Object>>) commentResponse.get("notes");

                // 첫번째 note는 Comment로 저장
                Map<String, Object> firstNote = notes.get(0);
                CommentEntity comment = new CommentEntity();
                comment.setCommentId((int) firstNote.get("id"));
                comment.setPrId((int) mrResponse.get("id"));
                comment.setRepoId((int) repositoryResponse.get("id"));
                comment.setContent((String) firstNote.get("body"));
                comment.setCommentType(0); // type 알아본 후 재설정하기
                // comment.setUserId();
                comment.setDisId((String) commentResponse.get("id"));
                comment.setCreateAt((String) firstNote.get("updated_at"));

                if(firstNote.get("position") != null){
                    Map<String, Object> position = (Map<String, Object>) firstNote.get("position");
                    comment.setNewLine((int) position.get("new_line"));
                    comment.setOldLine((int) position.get("old_line"));
                    Map<Map<String, Object>, Object> lineRange = (Map<Map<String, Object>, Object>) position.get("line_range");
                    Map<String, Object> start = (Map<String, Object>) lineRange.get("start");
                    Map<String, Object> end = (Map<String, Object>) lineRange.get("end");
                    comment.setNewStartLine((int) start.get("new_line"));
                    comment.setNewEndLine((int) end.get("new_line"));
                    comment.setOldStartLine((int) start.get("old_line"));
                    comment.setOldEndLine((int) end.get("old_line"));
                }
                commentRepository.save(comment);

                // ---------- Reply 가져오기 ---------- //
                // 2번째 note부터는 ReplyEntity로 저장
                for (int i = 1; i < notes.size(); i++) {
                    Map<String, Object> note = notes.get(i);
                    ReplyEntity reply = new ReplyEntity();
                    reply.setRepoId((int) repositoryResponse.get("id"));
                    reply.setPrId((int) mrResponse.get("id"));
                    reply.setCommentId((int) firstNote.get("id")); // 첫 번째 note의 commentId 저장
                    reply.setReCommentId((int) note.get("id"));
                    // reply.setUserId();
                    reply.setDisId((String) commentResponse.get("id"));
                    reply.setContent((String) note.get("body"));
                    reply.setReplyType(0);
                    reply.setCreateAt((Date) note.get("updated_at"));
                    replyRepository.save(reply);
                }

            }
        }
        return 1;
    }
}
