package com.giteat.pr.controller;


import com.giteat.pr.dto.CommentDto;
import com.giteat.pr.dto.FileDto;
import com.giteat.pr.entity.CommitEntity;
import com.giteat.pr.entity.PrEntity;
import com.giteat.pr.service.PrServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pr")
public class PrController {

    @Autowired
    @Qualifier("PrServiceImpl")
    private PrServiceImpl prService;

    @GetMapping("/{repoId}")
    @Operation(summary="PR 목록 확인", description = "PR의 목록을 확인합니다")
    public ResponseEntity<List<PrEntity>> getPrList(@PathVariable int repoId){
        List<PrEntity> prList = prService.findByRepoId(repoId);
        if(prList != null) {return ResponseEntity.ok(prList);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}")
    @Operation(summary="PR 정보 확인", description = "PR의 상세 정보를 확인합니다")
    public ResponseEntity<PrEntity> getPrById(@PathVariable int repoId, @PathVariable int prId) {
        PrEntity pr = prService.getPrById(repoId, prId);
        if(pr != null) {return ResponseEntity.ok(pr);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/commit")
    @Operation(summary="Commit 목록 확인", description = "PR내의 Commit 목록을 확인합니다")
    public ResponseEntity<List<CommitEntity>> getCommitList(@PathVariable int repoId, @PathVariable int prId) {
        List<CommitEntity> commitList = prService.getCommitList(repoId, prId);
        if(commitList !=null) {return ResponseEntity.ok(commitList);}
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/{repoId}/{prId}/commit/{commitId}")
    @Operation(summary="PR 정보 확인", description = "PR의 상세 정보를 확인합니다")
    public ResponseEntity<CommitEntity> getCommitById(@PathVariable int repoId, @PathVariable int prId, @PathVariable String commitId) {
        CommitEntity commit = prService.getCommitById(repoId, prId, commitId);
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
    public ResponseEntity<Integer> insertComment(@RequestBody CommentDto commentDto) {
        int result = prService.insertComment(commentDto);
        if(result !=0) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary="댓글 수정", description = "PR에 작성한 댓글을 수정합니다")
    public ResponseEntity<Integer> updateComment(@PathVariable int commentId, @RequestBody CommentDto commentDto) {
        commentDto.setCommentId(commentId);
        int result = prService.updateComment(commentDto);
        if(result !=0) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary="댓글 수정", description = "PR에 작성한 댓글을 수정합니다")
    public ResponseEntity<Integer> deleteComment(@PathVariable int repoId, @PathVariable int prId, @PathVariable int commentId) {
        int result = prService.deleteComment(repoId, prId, commentId);
        if(result !=0) {return ResponseEntity.ok(result);}
        return ResponseEntity.noContent().build();
    }

    // 대댓글 조회

    // 대댓글 등록

    // 대댓글 수정

    // 대댓글 삭제


    @GetMapping("/{repoId}/{prId}/file")
    @Operation(summary="파일 목록 조회", description = "PR내에 변경된 파일 목록을 조회합니다")
    public ResponseEntity<List<FileDto>> showFileList(@PathVariable int repoId, @PathVariable int prId) {
        List<FileDto> fileList = prService.showFileList(repoId, prId);
        if(fileList != null) {return ResponseEntity.ok(fileList);}
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/file/{fileId}")
    @Operation(summary="변경 된 코드 확인", description = "변경 된 파일의 코드를 조회합니다")
    public ResponseEntity<FileDto> showChangedCode(@PathVariable int repoId, @PathVariable int prId, @PathVariable int fileId) {
        FileDto file = prService.showChangedCode(repoId, prId, fileId);
        if(file != null) {return ResponseEntity.ok(file);}
        return ResponseEntity.noContent().build();
    }
}
