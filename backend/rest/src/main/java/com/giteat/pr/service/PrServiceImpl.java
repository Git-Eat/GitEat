package com.giteat.pr.service;

import com.giteat.api.LabApi;
import com.giteat.pr.dto.*;
import com.giteat.pr.mapper.PrMapper;
import com.giteat.repo.entity.MergeRequestEntity;
import com.giteat.repo.repository.MergeRequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service("PrServiceImpl")
@RequiredArgsConstructor
public class PrServiceImpl implements PrService{

    private final PrMapper prMapper;
    private final CommentConverter commentConverter;
    private final MergeRequestRepository mergeRequestRepository;
    private final LabApi gitLabApi;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<PrDto> getPrList (int repoId , String accessToken) {
        return prMapper.getPrList(repoId);
    }

    @Transactional
    @Override
    public PrDto getPrById(int repoId, int prId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);

        // pr정보 조회 후 sha값 있는지 확인
        PrDto prInfo = prMapper.getPrById(params);

        Map<String, Object> reponse = gitLabApi.getMergeRequestsById(String.valueOf(repoId), String.valueOf(prId), accessToken);
        Map<String, Object> diff_refs = (Map<String, Object>) reponse.get("diff_refs");
        String base_sha = (String) diff_refs.get("base_sha");
        String head_sha = (String) diff_refs.get("head_sha");
        String start_sha = (String) diff_refs.get("start_sha");
        prInfo.setBaseSha(base_sha);
        prInfo.setHeadSha(head_sha);
        prInfo.setStartSha(start_sha);

        System.out.println("base 출력" + base_sha);
        System.out.println("head 출력" + head_sha);
        System.out.println("start 출력" + start_sha);

        // DB에도 업데이트
        Map<String, Object> params2 = new HashMap<>();
        params2.put("repoId", repoId);
        params2.put("prId", prId);
        params2.put("baseSha", base_sha);
        params2.put("headSha", head_sha);
        params2.put("startSha", start_sha);
        int result = prMapper.updateShaInfo(params2);
        if(result == 1) System.out.println("업데이트 완료");



        // JPA를 이용해 해당 PR 엔티티 조회 후, refresh 처리
        Optional<MergeRequestEntity> optionalEntity = mergeRequestRepository.findByRepoIdAndPrId(repoId, prId);
        if (optionalEntity.isPresent()) {
            MergeRequestEntity existingMr = optionalEntity.get();
            existingMr.setHeadSha(head_sha);
            existingMr.setBaseSha(base_sha);
            existingMr.setStartSha(start_sha);
            System.out.println("Entity 업데이트 완료");
            mergeRequestRepository.save(existingMr);
            entityManager.flush();
        }


        
        return prInfo;
    }

    @Override
    public List<CommitDto> getCommitList(int repoId,int prId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return prMapper.getCommitList(params);
    }

    @Override
    public CommitDto getCommitById(int repoId, int prId, String commitId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("commitId", commitId);
        return prMapper.getCommitById(params);
    }

    @Override
    public List<CommentDto> getCommentList(int repoId, int prId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        List<CommentDto> comments = prMapper.getCommentList(params);
        for(CommentDto comment : comments){
            if(comment.getPosition().getNewLine()==0 && comment.getPosition().getOldLine() ==0){
                comment.setPosition(null);
            }
        }
        return comments;
    }


    @Override
    public CommentDto insertComment(String repoId, String prId, CommentDto commentDto , String accessToken) {
        // GitLab API에 댓글 등록 요청
        Map<String,Object> response = gitLabApi.insertComment(repoId, prId, commentDto.getContent(), accessToken);
        if(response != null) {
            commentDto.setCommentId((int) response.get("id"));
            commentDto.setRepoId(Integer.parseInt(repoId));
            commentDto.setPrId(Integer.parseInt(prId));
            commentDto.setContent((String) response.get("body"));
            commentDto.setCreateAt((String) response.get("created_at"));
            Map<String, Object> author = (Map<String, Object>) response.get("author");
            commentDto.setUserId((int) author.get("id"));
            commentDto.setUserName((String) author.get("name"));
            commentDto.setAvatarUrl((String) author.get("avatar_url"));
            return commentDto;
        }
        return null;
    }

    @Override
    public CommentDto updateComment(String repoId, String prId, CommentDto commentDto , String accessToken) {
        // GitLab API에 댓글 수정 요청
        Map<String,Object> response = gitLabApi.updateComment(repoId, prId,  String.valueOf(commentDto.getCommentId()) ,commentDto.getContent(),accessToken
        );
        if(response != null) {
            commentDto.setContent((String) response.get("body"));
            commentDto.setCreateAt((String) response.get("updated_at"));
            return commentDto;
        }
        return null;
    }

    @Override
    public int deleteComment(String repoId, String prId, String commentId , String accessToken) {

        // GitLab API에 댓글 삭제 요청
        boolean response = gitLabApi.deleteComment(repoId, prId, commentId,accessToken);

        // 우리 DB에서도 삭제
        if(response){
            Map<String, Object> params = new HashMap<>();
            params.put("repoId", repoId);
            params.put("prId", prId);
            params.put("commentId", commentId);
            return prMapper.deleteComment(params);
        }
        return 404;
    }

    @Override
    public Map<String, String> uploadsFile(String repoId, MultipartFile file , String accessToken) {

        Map<String, String> fileData;
        try {
            fileData = gitLabApi.uploadFile(repoId, file , accessToken); // 깃랩 이미지 업로드 API 호출
        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
        return fileData;
    }

    @Override
    public CommentDto insertFileComment(String repoId, String prId, CustomCommentDto customCommentDto , String accessToken) {
        // RequestBody 데이터 변환
        Map<String, Object> gitLabRequest = commentConverter.converToGitLabFormat(customCommentDto);

        // 깃랩 API에 댓글 등록 요청
        Map<String,Object> response = gitLabApi.insertFileComment(repoId, prId, gitLabRequest, accessToken);
        if(response != null) {
            CommentDto comment = new CommentDto();
            List<Map<String, Object>> notes = (List<Map<String, Object>>) response.get("notes");
            Map<String, Object> noteInfo = (Map<String, Object>) notes.get(0);
            Map<String, Object> author = (Map<String, Object>) noteInfo.get("author");

            comment.setCommentId((int) noteInfo.get("id"));
            comment.setPrId(Integer.parseInt(prId));
            comment.setRepoId(Integer.parseInt(repoId));
            comment.setUserId((int) author.get("id"));
            comment.setFileId(customCommentDto.getFileId());
            comment.setUserName((String) author.get("name"));
            comment.setAvatarUrl((String) author.get("avatar_url"));
            comment.setDisId((String) response.get("id"));
            comment.setContent((String) noteInfo.get("body"));
            comment.setCommentType(0);
            comment.setCreateAt((String) noteInfo.get("created_at"));
            // 여기에 Position 값 넣어야 하나 ? >> 넣어서 주기

            FileCommentDto.Position position = new FileCommentDto.Position();
            Map<String, Object> positionResponse = (Map<String, Object>) noteInfo.get("position");
            position.setBaseSha((String) positionResponse.get("base_sha"));
            position.setHeadSha((String) positionResponse.get("head_sha"));
            position.setStartSha((String) positionResponse.get("start_sha"));
            position.setOldPath((String) positionResponse.get("old_path"));
            position.setNewPath((String) positionResponse.get("new_path"));
            position.setPositionType("text");
            if(positionResponse.get("new_line") == null) {
                position.setNewLine(null);
            } else {
                position.setNewLine((int) positionResponse.get("new_line"));
            }

            if(positionResponse.get("old_line") == null) {
                position.setOldLine(null);
            } else {
                position.setOldLine((int) positionResponse.get("old_line"));
            }
            position.setNewStartLine(customCommentDto.getNewStartLine());
            position.setNewEndLine(customCommentDto.getNewEndLine());
            position.setOldStartLine(customCommentDto.getOldStartLine());
            position.setOldEndLine(customCommentDto.getOldEndLine());

//            FileCommentDto.Position.LineRange lineRange = new FileCommentDto.Position.LineRange();
//            FileCommentDto.Position.LineCode lineCode = new FileCommentDto.Position.LineCode();

            comment.setPosition(position);
            return comment;
        }
        return null;
    }

    @Override
    public List<ReplyDto> showReply(int repoId, int prId, int commentId , String accessToken) {
        return prMapper.showReply(repoId, prId, commentId);
    }

    @Override
    public ReplyReturnDto insertReply(String repoId, String prId, String discussionId, ReplyDto replyDto , String accessToken) {
        Map<String,Object> response = gitLabApi.insertReply(repoId, prId, discussionId, replyDto.getContent(), accessToken);
        if(response != null) {
            ReplyReturnDto reply = new ReplyReturnDto();
            Map<String, Object> author = (Map<String, Object>) response.get("author");
            reply.setReCommentId((int) response.get("id"));
            reply.setCommentId(replyDto.getCommentId());
            reply.setUserId((int) author.get("id"));
            reply.setRepoId(Integer.parseInt(repoId));
            reply.setPrId(Integer.parseInt(prId));
            reply.setDiscussionId(discussionId);
            reply.setUserName((String) author.get("name"));
            reply.setAvatarUrl((String) author.get("avatar_url"));
            reply.setReCommentType(0);
            reply.setContent((String) response.get("body"));
            reply.setCreateAt((String) response.get("created_at"));
            return reply;
        }
        return null;
    }

    @Override
    public ReplyReturnDto updateReply(String repoId, String prId, String reCommentId, ReplyDto replyDto , String accessToken) {
        Map<String,Object> response = gitLabApi.updateReply(repoId, prId, reCommentId, replyDto.getContent(), accessToken);
        if(response != null) {
            ReplyReturnDto reply = new ReplyReturnDto();
            Map<String, Object> author = (Map<String, Object>) response.get("author");
            reply.setReCommentId((int) response.get("id"));
            reply.setUserId((int) author.get("id"));
            reply.setRepoId(Integer.parseInt(repoId));
            reply.setPrId(Integer.parseInt(prId));
            reply.setUserName((String) author.get("name"));
            reply.setAvatarUrl((String) author.get("avatar_url"));
            reply.setReCommentType(0);
            reply.setContent((String) response.get("body"));
            reply.setCreateAt((String) response.get("updated_at"));
            return reply;
        }
        return null;
    }

    @Override
    public int deleteReply(String repoId, String prId, String reCommentId , String accessToken) {
        // GitLab API에 댓글 삭제 요청
        boolean response = gitLabApi.deleteComment(repoId, prId, reCommentId,accessToken);

        // 우리 DB에서도 삭제
        if(response){
            Map<String, Object> params = new HashMap<>();
            params.put("repoId", repoId);
            params.put("prId", prId);
            params.put("reCommentId", reCommentId);
            return prMapper.deleteReply(params);
        }
        return 404;
    }

    @Override
    public List<FileDto> showFileListByPr(int repoId, int prId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return prMapper.showFileListByPr(params);
    }

    @Override
    public List<FileDto> showFileListByCommit(int repoId, int prId, String commitId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("commitId", commitId);
        return prMapper.showFileListByCommit(params);
    }

    @Override
    public Map<String, Object> showChangedCode(String repoId, String prId, FileDto fileDto , String accessToken) {

        // 1. DB에서 fileId를 기준으로 commit_id, new_path, old_path 가져오기
        String newPath = fileDto.getNewPath();
        String oldPath = fileDto.getOldPath();
        int status = fileDto.getFileStatus();

        String encodedNewPath = encodePath(newPath);
        String encodedOldPath = encodePath(oldPath);

        String newRawFile = null;
        String oldRawFile = null;

        // 2. PR에서 sha값 있는지 확인 후 , 없으면 요청 후 DB에 저장
        Map<String, Object> params2 = new HashMap<>();
        params2.put("repoId", repoId);
        params2.put("prId", prId);
        PrDto pr = prMapper.getPrById(params2);

        String base_sha = pr.getBaseSha();
        String head_sha = pr.getHeadSha();

//        if (optionalMr.isPresent()) {
//            MergeRequestEntity existingMr = optionalMr.get();
//            // 값이 없는 경우
//            if(existingMr.getBaseSha()==null || existingMr.getHeadSha()==null){
//                Map<String, Object> mrResponse = gitLabApi.getMergeRequestsById(repoId, String.valueOf(fileDto.getPrId()), accessToken);
//                Map<String, Object> shaInfo = (Map<String, Object>) mrResponse.get("diff_refs");
//                existingMr.setBaseSha((String) shaInfo.get("base_sha"));
//                existingMr.setHeadSha((String) shaInfo.get("head_sha"));
//                existingMr.setStartSha((String) shaInfo.get("start_sha"));// sha값이 없다면 개별 조회 호출해서 sha값 업데이트
//
//                base_sha = (String) shaInfo.get("base_sha");
//                head_sha = (String) shaInfo.get("head_sha");
//                System.out.println("값이 없어용");
//                System.out.println(head_sha);
//                System.out.println(base_sha);
//            } else {
//                base_sha = existingMr.getBaseSha();
//                head_sha = existingMr.getHeadSha();
//                System.out.println("값이 있어용");
//                System.out.println(head_sha);
//                System.out.println(base_sha);
//            }
//            mergeRequestRepository.save(existingMr); // 업데이트
//    }


            if(status == 1){
                // 파일이 추가 된 경우, fileStatus = 1
                newRawFile = gitLabApi.getRawCode(repoId,encodedNewPath,head_sha, accessToken);
            } else if(status ==2) {
                // 파일 내용이 수정된 경우, fileStatus = 2
                oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, base_sha, accessToken);
                newRawFile = gitLabApi.getRawCode(repoId,encodedNewPath,head_sha, accessToken);
            } else if(status==3){
                // 파일이 삭제 된 경우,  fileStatus = 3
                oldRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, base_sha, accessToken);
                System.out.println(oldRawFile);
            } else if(!oldPath.equals(newPath)){
                // 파일 경로가 수정된 경우
                oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, base_sha,accessToken);
                newRawFile = gitLabApi.getRawCode(repoId,encodedNewPath,head_sha, accessToken);
            }



        // 3. 해당 파일에 달린 댓글 가져오기 > 얘는 Mapper 호출
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        params.put("fileId", fileDto.getFileId());
        List<CommentDto> fileComments = prMapper.getCommentListByCode(params);
        for(CommentDto comment : fileComments){
            if(comment.getPosition().getNewLine()==0 && comment.getPosition().getOldLine() ==0){
                comment.setPosition(null);
            }
        }

        // 결과를 MAP으로 반환
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", fileDto.getFileName());
        result.put("oldCode", oldRawFile);
        result.put("newCode", newRawFile);
        result.put("comments", fileComments);  // 댓글 리스트 추가
        return result;
    }

    @Override
    public List<ReviewerDto> getReviewer(String repoId, String prId , String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("repoId", repoId);
        params.put("prId", prId);
        return  prMapper.getReviewer(params);
    }


    // 파일 경로를 URL 인코딩하는 함수
    private String encodePath(String path) {
        try {
            return URLEncoder.encode(path, StandardCharsets.UTF_8).replace("/", "%2F");
        } catch (Exception e) {
            throw new RuntimeException("파일 경로 인코딩 실패: " + path, e);
        }
    }
}
