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
import java.util.Optional;

@Service("RepoServiceImpl")
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService{

    private final RepoRepository repoRepository;
    private final MergeRequestRepository mergeRequestRepository;
    private final CommitRepository commitRepository;
    private final FileChangeRepository fileChangeRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UsersRepository usersRepository;
    private final RepositoryMemberRepository repositoryMemberRepository;
    private final LabApi gitLabApi;

    @Override
    public List<RepositoryEntity> getRepoList() {
        String accessToken = "UATEgVcVTSsLn7PWao6c";
        Map<String, Object> userResponse = gitLabApi.getUser(accessToken); // user 정보 불러오는 Endpoint 호출
        int userId = (int) userResponse.get("id");
        return repoRepository.getRepoList(userId);
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
        String accessToken = "UATEgVcVTSsLn7PWao6c";
        Map<String, Object> userResponse = gitLabApi.getUser(accessToken); // user 정보 불러오는 Endpoint 호출
        int userId = (int) userResponse.get("id");
        return repoRepository.deleteRepo(repoId, userId);
    }

    @Override
    public RepositoryEntity saveRepositoryData(String accessToken , String projectId){
        int repoId = Integer.parseInt(projectId);

        // ---------- members 정보 가져오기 ------------ //
        UsersEntity user = new UsersEntity();
        List<Map<String,Object>> membersResponse = gitLabApi.getMembers(projectId, accessToken);
        for(Map<String, Object> member : membersResponse){
            int memberId = (int) member.get("id");  // GitLab 멤버 ID
            String email = (String) member.get("email");  // GitLab 이메일
            String name = (String) member.get("name");  // GitLab 한글 이름
            String userName = (String) member.get("username"); // 사용자 이름
            String avatarUrl = (String) member.get("avatar_url");  // GitLab 프로필 사진

            // 1. users 테이블에서 user_id가 존재하는지 확인
            Optional<UsersEntity> optionalUser = usersRepository.findByUserId(memberId);

            if (optionalUser.isPresent()) {
                // 2. 이미 존재하면 정보 업데이트
                UsersEntity existingUser = optionalUser.get();
                existingUser.setEmail(email);
                existingUser.setName(name);
                existingUser.setUserName(userName);
                existingUser.setAvatarUrl(avatarUrl);
                usersRepository.save(existingUser); // 업데이트
            } else {
                // 3. 존재하지 않으면 새로운 사용자 저장
                UsersEntity newUser = new UsersEntity();
                newUser.setUserId(memberId);
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setUserName(userName);
                newUser.setAvatarUrl(avatarUrl);
                usersRepository.save(newUser); // 신규 저장
            }
        }

        Map<String, Object> userResponse = gitLabApi.getUser(accessToken); // user 정보 불러오는 Endpoint 호출
        int userId = (int) userResponse.get("id");
        RepositoryMemberEntity repositoryMemberEntity = new RepositoryMemberEntity();

        // repo 정보 있는지 확인 > 있다면 repo_member만 업데이트 하고 저장 //
        RepositoryEntity repositoryInfo = repoRepository.findByRepoId(repoId);
        if(repositoryInfo != null){
            // repo_member에 저장
            RepositoryMemberId repositoryMemberId = new RepositoryMemberId(repoId, userId);
            System.out.println("repo log");
            repositoryMemberEntity.setId(repositoryMemberId);
            repositoryMemberRepository.save(repositoryMemberEntity);
            System.out.println("이미 정보가 있는 repo입니다." + repositoryInfo);
            System.out.println("레포 아이디: " + repositoryInfo.getName());
            return repositoryInfo;
        }


        // ---------- repo 정보부터 가져오기 ----------- //
        RepositoryEntity repository = new RepositoryEntity();
        Map<String, Object> repositoryResponse = gitLabApi.getRepository(projectId,accessToken);
        Map<String, Object> nameSpace = (Map<String, Object>) repositoryResponse.get("namespace");
        repository.setRepoId((Integer) repositoryResponse.get("id"));
        repository.setName((String) repositoryResponse.get("name"));
        repository.setDescription((String) repositoryResponse.get("description"));
        repository.setGitlabUrl((String) repositoryResponse.get("web_url"));
        repository.setCreateAt((String) repositoryResponse.get("created_at"));
        repository.setOwnerName((String) nameSpace.get("name"));

        if(repositoryResponse.get("visibility").equals("private")) repository.setAccess(1);
        else if(repositoryResponse.get("visibility").equals("public")) repository.setAccess(2);
        else repository.setAccess(3);

        repoRepository.save(repository);


        // -------------- RepositoryMember 업데이트 ------------------- //
        RepositoryMemberId repositoryMemberId = new RepositoryMemberId(repoId, userId);
        repositoryMemberEntity.setId(repositoryMemberId);
        repositoryMemberRepository.save(repositoryMemberEntity);
        
        // ---------- MR 정보 가져오기 ---------- //
        List<Map<String, Object>> newMrResponse = gitLabApi.getMergeRequests(projectId, accessToken);
        if(newMrResponse.isEmpty()) {return repository;} // MR 없으면 아래 다 건너뛰어

        int newMR = (int) newMrResponse.get(0).get("iid"); // 최상단 MR 번호
        int pageNation = Math.max(1, (newMR + 99) / 100); // 100으로 나눈 몫 저장 (이 기준으로 pageNation 요청할거임)

        for(int page = 1; page <= pageNation; page++){
            List<Map<String, Object>> mrResponseList = gitLabApi.getMergeRequestsByPageNation(projectId,page,accessToken);
            for(Map<String, Object> mrResponse : mrResponseList){
                Map<String, Object> author =  (Map<String, Object>) mrResponse.get("author");
                MergeRequestEntity mr = new MergeRequestEntity();
                MergeRequestId mrId = new MergeRequestId((Integer) mrResponse.get("iid"), Integer.parseInt(projectId));
                mr.setId(mrId);
                mr.setTitle((String) mrResponse.get("title"));
                mr.setDescription((String) mrResponse.get("description"));
                mr.setCreateAt((String) mrResponse.get("created_at"));

                if(mrResponse.get("state").equals("merged"))  mr.setIsOpened(1); // 병합
                else if(mrResponse.get("state").equals("closed")) mr.setIsOpened(3); // 닫힘
                else mr.setIsOpened(2); // 열려있음

                mr.setSourceBranch((String) mrResponse.get("source_branch"));
                mr.setTargetBranch((String) mrResponse.get("target_branch"));
                mr.setUserId((int) author.get("id"));
                mr.setUserName((String) author.get("name"));
                mr.setUserProfile((String) author.get("avatar_url"));
                mergeRequestRepository.save(mr);

                // ---------- Commit 정보 가져오기 ---------- //
                List<Map<String, Object>> CommitList = gitLabApi.getCommits(projectId,(Integer) mrResponse.get("iid"), accessToken);
                for(Map<String, Object> commitResponse : CommitList){
                    CommitEntity commitEntity = new CommitEntity();
                    CommitId commitId = new CommitId((String) commitResponse.get("id"), (Integer) repositoryResponse.get("id"),(Integer) mrResponse.get("iid"));
                    commitEntity.setId(commitId);
                    commitEntity.setContent((String) commitResponse.get("message"));
                    commitEntity.setCommitedAt((String) commitResponse.get("committed_date"));
                    commitRepository.save(commitEntity);
                }

                // ---------- FileChange 가져오기 (PR기준) ---------- //
                // 일단 pageNation은 20까지만 했음 PR한번에 filechange 400개까지 받아오는거 가능//

                for(int prPageNation = 1; prPageNation <=20; prPageNation++){
                    List<Map<String, Object>> fileChangeList = gitLabApi.getFilesByPr(projectId, (Integer) mrResponse.get("iid"), prPageNation, accessToken);
                    if(fileChangeList.isEmpty()) break; // 배열이 비어있다면(받아온 값이 없다면) for문 탈출

                    for (Map<String, Object> fileChange : fileChangeList) {
                        FileChangeEntity fileChangeEntity = new FileChangeEntity();
                        FileChangeId fileChangeId = new FileChangeId(SHA1Util.encryptSHA1((String) fileChange.get("new_path")),
                                (int) repositoryResponse.get("id"), (int) mrResponse.get("iid"));

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

                // ---------- Comment 가져오기 ---------- //
                List<Map<String, Object>> CommentList = gitLabApi.getDiscussions(projectId, (Integer) mrResponse.get("iid"), accessToken);
                for(Map<String, Object> commentResponse : CommentList){
                    //if((boolean) commentResponse.get("individual_note")) continue; // individual_note값이 false 일때만 DB에 저장

                    List<Map<String, Object>> notes = (List<Map<String, Object>>) commentResponse.get("notes");

                    // 첫번째 note는 Comment로 저장
                    Map<String, Object> firstNote = notes.get(0);
                    CommentEntity comment = new CommentEntity();
                    CommentId commentId = new CommentId((int) firstNote.get("id"), (int) mrResponse.get("iid"), (int) repositoryResponse.get("id"));
                    Map<String, Object> commentAuthor = (Map<String, Object>) notes.get(0).get("author");

                    comment.setId(commentId);
                    comment.setContent((String) firstNote.get("body"));
                    comment.setCommentType(0); // type 알아본 후 재설정하기
                    comment.setUserId((int) commentAuthor.get("id"));
                    comment.setDisId((String) commentResponse.get("id"));
                    comment.setCreateAt((String) firstNote.get("updated_at"));

                    if(firstNote.get("position") != null){
                        Map<String, Object> position = (Map<String, Object>) firstNote.get("position");
                        if(position.get("new_line") !=null) comment.setNewLine((int) position.get("new_line"));
                        if(position.get("old_line") !=null) comment.setOldLine((int) position.get("old_line"));

                        Optional<MergeRequestEntity> optionalMr = mergeRequestRepository.findById_PrId((Integer) mrResponse.get("iid"));

                        if (optionalMr.isPresent()) {
                            // MR 정보 업데이트
                            MergeRequestEntity existingMr = optionalMr.get();
                            existingMr.setBaseSha((String) position.get("base_sha"));
                            existingMr.setHeadSha((String) position.get("head_sha"));
                            existingMr.setStartSha((String) position.get("start_sha"));
                            mergeRequestRepository.save(existingMr); // 업데이트
                        }

                        Map<String, Object> lineRange = (Map<String, Object>) position.get("line_range");
                        if(lineRange != null){
                            Map<String, Object> start = (Map<String, Object>) lineRange.get("start");
                            Map<String, Object> end = (Map<String, Object>) lineRange.get("end");
                            if(start.get("new_line") !=null)  comment.setNewStartLine((int) start.get("new_line"));
                            if(end.get("new_line") !=null) comment.setNewEndLine((int) end.get("new_line"));
                            if(start.get("old_line") !=null) comment.setOldStartLine((int) start.get("old_line"));
                            if(end.get("old_line") !=null) comment.setOldEndLine((int) end.get("old_line"));
                        }
                    }
                    commentRepository.save(comment);

                    // ---------- Reply 가져오기 ---------- //
                    // 2번째 note부터는 ReplyEntity로 저장
                    for (int i = 1; i < notes.size(); i++) {
                        Map<String, Object> note = notes.get(i);
                        Map<String, Object> replyAuthor = (Map<String, Object>) notes.get(i).get("author");
                        ReplyEntity reply = new ReplyEntity();
                        ReplyId replyId = new ReplyId((int) note.get("id"), (int) firstNote.get("id"), (int) mrResponse.get("iid"), (int) repositoryResponse.get("id"));
                        reply.setId(replyId);
                        reply.setUserId((int) replyAuthor.get("id"));
                        reply.setDisId((String) commentResponse.get("id"));
                        reply.setContent((String) note.get("body"));
                        reply.setReCommentType(1);
                        reply.setCreateAt((String) note.get("updated_at"));
                        replyRepository.save(reply);
                    }

                }

            }

        }
        return repository;
    }
}
