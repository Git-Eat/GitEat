package com.giteat.pr.controller;


import com.giteat.pr.dto.*;
import com.giteat.pr.service.PrServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rest/pr")
public class PrController {

    @Autowired
    @Qualifier("PrServiceImpl")
    private PrServiceImpl prService;

    @GetMapping("/{repoId}")
    @Operation(summary="PR 목록 확인", description = "PR의 목록을 확인합니다")
    public ResponseEntity<List<PrDto>> getPrList(@PathVariable int repoId){
        List<PrDto> prList = prService.getPrList(repoId);
        if(prList != null) {return ResponseEntity.ok(prList);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}")
    @Operation(summary="PR 정보 확인", description = "PR의 상세 정보를 확인합니다")
    public ResponseEntity<PrDto> getPrById(@PathVariable int repoId, @PathVariable int prId) {
        PrDto pr = prService.getPrById(repoId, prId);
        if(pr != null) {return ResponseEntity.ok(pr);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/commit")
    @Operation(summary="Commit 목록 확인", description = "PR내의 Commit 목록을 확인합니다")
    public ResponseEntity<List<CommitDto>> getCommitList(@PathVariable int repoId, @PathVariable int prId) {
        List<CommitDto> commitList = prService.getCommitList(repoId,prId);
        if(commitList !=null) {return ResponseEntity.ok(commitList);}
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/{repoId}/{prId}/commit/{commitId}")
    @Operation(summary="PR 정보 확인", description = "PR의 상세 정보를 확인합니다")
    public ResponseEntity<CommitDto> getCommitById(@PathVariable int repoId,@PathVariable int prId, @PathVariable String commitId) {
        CommitDto commit = prService.getCommitById(repoId, prId, commitId);
        if(commit != null) {return ResponseEntity.ok(commit);}
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/{repoId}/{prId}/comment")
    @Operation(summary="댓글 조회", description = "PR에 생성된 댓글을 조회합니다")
    public ResponseEntity<List<CommentDto>> getCommentList(@PathVariable int repoId, @PathVariable int prId) {
        List<CommentDto> comments = prService.getCommentList(repoId, prId);
        if(comments != null) {return ResponseEntity.ok(comments);}
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{repoId}/{prId}/comment")
    @Operation(summary="댓글 등록", description = "PR에 댓글을 등록합니다")
    public ResponseEntity<Integer> insertComment(@PathVariable int repoId,
                                                 @PathVariable int prId,
                                                 @RequestBody CommentDto commentDto) {
        int result = prService.insertComment(repoId, prId, commentDto);
        if(result==200) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary="댓글 수정", description = "PR에 작성한 댓글을 수정합니다")
    public ResponseEntity<Integer> updateComment(@PathVariable int repoId,
                                                 @PathVariable int prId,
                                                 @PathVariable int commentId,
                                                 @RequestBody CommentDto commentDto) {
        commentDto.setCommentId(commentId);
        int result = prService.updateComment(repoId, prId, commentDto);
        if(result ==200) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary="댓글 삭제", description = "PR에 작성한 댓글을 삭제합니다")
    public ResponseEntity<Integer> deleteComment(@PathVariable int repoId, @PathVariable int prId, @PathVariable int commentId) {
        int result = prService.deleteComment(repoId, prId, commentId);
        if(result !=0) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{repoId}/{prId}/comment/{commentId}/reply")
    @Operation(summary = "대댓글 조회", description = "대댓글을 조회합니다")
    public ResponseEntity<List<ReplyDto>> showReply(@PathVariable int repoId, @PathVariable int prId, @PathVariable int commentId){
        List<ReplyDto> replies = prService.showReply(repoId, prId, commentId);
        if(replies != null) return ResponseEntity.ok(replies);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{repoId}/{prId}/reply/{discussionId}")
    @Operation(summary = "대댓글 등록", description = "대댓글을 등록합니다")
    public ResponseEntity<Integer> insertReply(@PathVariable String repoId,
                                               @PathVariable String prId,
                                               @PathVariable String discussionId,
                                               @RequestBody ReplyDto replyDto){
        int result = prService.insertReply(repoId, prId, discussionId, replyDto);
        if(result==200) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{repoId}/{prId}/reply/{replyId}")
    @Operation(summary = "대댓글 수정", description = "대댓글을 수정합니다")
    public ResponseEntity<Integer> updateReply(@PathVariable String repoId,
                                               @PathVariable String prId,
                                               @PathVariable String replyId,
                                               @RequestBody ReplyDto replyDto){
        int result = prService.updateReply(repoId, prId, replyId, replyDto);
        if(result==200) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}/reply/{replyId}")
    @Operation(summary = "대댓글 삭제", description = "대댓글을 삭제합니다")
    public ResponseEntity<Integer> deleteReply(@PathVariable int replyId){
        //replyId는 note_id
        int result = prService.deleteReply(replyId);
        if(result !=0) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/file")
    @Operation(summary="파일 목록 조회(PR)", description = "PR내에 변경된 모든 파일 목록을 조회합니다")
    public ResponseEntity<List<FileDto>> showFileListByPr(@PathVariable int repoId, @PathVariable int prId) {
        List<FileDto> fileList = prService.showFileListByPr(repoId, prId);
        if(fileList != null) {return ResponseEntity.ok(fileList);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/file/{commitId}")
    @Operation(summary="파일 목록 조회(commit별)", description = "Commit내에 변경된 파일 목록을 조회합니다")
    public ResponseEntity<List<FileDto>> showFileListByCommit(@PathVariable int repoId, @PathVariable int prId, @PathVariable String commitId) {
        List<FileDto> fileList = prService.showFileListByCommit(repoId, prId, commitId);
        if(fileList != null) {return ResponseEntity.ok(fileList);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/file/raw/{fileId}")
    @Operation(summary="변경 된 코드 확인", description = "변경 된 파일의 전 후 코드를 조회합니다")
    public ResponseEntity<Map<String, String>> showChangedCode(@PathVariable String repoId,
                                                               @PathVariable String prId,
                                                               @PathVariable int fileId) {
        Map<String, String> changedCode = prService.showChangedCode(repoId, prId, fileId);
        if(changedCode != null && !changedCode.isEmpty()) {return ResponseEntity.ok(changedCode);}
        return ResponseEntity.noContent().build();
    }

    /**
     * 등록한 레포지토리 정보 읽어 오기
     * @param accessToken
     * @param repositoryId
     * @return
     */
    @PostMapping("/repositoryData")
    @Operation(summary="repository의 모든 데이터 읽기", description = "repository에서 모든 데이터를 가져옵니다.")
    public ResponseEntity<?> saveRepositoryData(@RequestHeader("accessToken") String accessToken , @RequestBody String repositoryId){
        
        return ResponseEntity.ok().build();
    }
}
