package com.giteat.pr.controller;

import com.giteat.pr.dto.*;
import com.giteat.pr.service.PrServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pr")
public class PrController {

    @Autowired
    @Qualifier("PrServiceImpl")
    private PrServiceImpl prService;

    @GetMapping("/{repoId}")
    @Operation(summary = "PR 목록 확인", description = "PR의 목록을 확인합니다")
    public ResponseEntity<List<PrDto>> getPrList(@RequestHeader(value = "Authorization") String header,
                                                 @PathVariable int repoId) {
        String accessToken = header.split(" ")[1];
        List<PrDto> prList = prService.getPrList(repoId, accessToken);
        if (prList != null) {
            return ResponseEntity.ok(prList);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}")
    @Operation(summary = "PR 정보 확인", description = "PR의 상세 정보를 확인합니다")
    public ResponseEntity<PrDto> getPrById(@RequestHeader(value = "Authorization") String header,
                                           @PathVariable int repoId, @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        PrDto pr = prService.getPrById(repoId, prId, accessToken);
        if (pr != null) {
            return ResponseEntity.ok(pr);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/commit")
    @Operation(summary = "Commit 목록 확인", description = "PR내의 Commit 목록을 확인합니다")
    public ResponseEntity<List<CommitDto>> getCommitList(@RequestHeader(value = "Authorization") String header,
                                                         @PathVariable int repoId, @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        List<CommitDto> commitList = prService.getCommitList(repoId, prId, accessToken);
        if (commitList != null) {
            return ResponseEntity.ok(commitList);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/commit/{commitId}")
    @Operation(summary = "PR 정보 확인", description = "PR의 상세 정보를 확인합니다")
    public ResponseEntity<CommitDto> getCommitById(@RequestHeader(value = "Authorization") String header,
                                                   @PathVariable int repoId, @PathVariable int prId,
                                                   @PathVariable String commitId) {
        String accessToken = header.split(" ")[1];
        CommitDto commit = prService.getCommitById(repoId, prId, commitId, accessToken);
        if (commit != null) {
            return ResponseEntity.ok(commit);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "댓글 조회", description = "PR에 생성된 댓글을 조회합니다(댓글, 대댓글, 코드댓글 포함)")
    public ResponseEntity<List<CommentDto>> getCommentList(@RequestHeader(value = "Authorization") String header,
                                                           @PathVariable int repoId, @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        List<CommentDto> comments = prService.getCommentList(repoId, prId, accessToken);
        if (comments != null) {
            return ResponseEntity.ok(comments);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "댓글 등록", description = "PR에 댓글을 등록합니다")
    public ResponseEntity<CommentDto> insertComment(@RequestHeader(value = "Authorization") String header,
                                                 @PathVariable String repoId, @PathVariable String prId,
                                                 @RequestBody CommentDto commentDto) {
        String accessToken = header.split(" ")[1];
        CommentDto response = prService.insertComment(repoId, prId, commentDto, accessToken);
        if (response!= null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{repoId}/uploads")
    @Operation(summary = "파일 업로드", description = "파일 업로드 하면 markdown을 return합니다")
    public ResponseEntity<Map<String, String>> uploadsFile(@RequestHeader(value = "Authorization") String header,
                                                           @PathVariable String repoId,
                                                           @RequestParam(value = "file", required = false) MultipartFile file) {
        String accessToken = header.split(" ")[1];
        Map<String, String> result = prService.uploadsFile(repoId, file, accessToken);
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{repoId}/{prId}/file/comment")
    @Operation(summary = "코드에 댓글 등록", description = "파일 코드에 라인별로 댓글을 등록합니다")
    public ResponseEntity<?> insertFileComment(@RequestHeader(value = "Authorization") String header,
                                               @PathVariable String repoId, @PathVariable String prId,
                                               @RequestBody CustomCommentDto customCommentDto) {
        String accessToken = header.split(" ")[1];
        String result = prService.insertFileComment(repoId, prId, customCommentDto, accessToken);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "댓글 수정", description = "PR에 작성한 댓글을 수정합니다")
    public ResponseEntity<Integer> updateComment(@RequestHeader("Authorization") String header,
                                                 @PathVariable int repoId, @PathVariable int prId,
                                                 @PathVariable int commentId, @RequestBody CommentDto commentDto) {
        String accessToken = header.split(" ")[1];
        commentDto.setCommentId(commentId);
        int result = prService.updateComment(repoId, prId, commentDto, accessToken);
        if (result == 200) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "댓글 삭제", description = "PR에 작성한 댓글을 삭제합니다")
    public ResponseEntity<Integer> deleteComment(@RequestHeader(value = "Authorization") String header,
                                                 @PathVariable String repoId, @PathVariable String prId,
                                                 @PathVariable String commentId) {
        String accessToken = header.split(" ")[1];
        int result = prService.deleteComment(repoId, prId, commentId, accessToken);
        if (result != 0) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/comment/{commentId}/reply")
    @Operation(summary = "대댓글 조회", description = "대댓글을 조회합니다")
    public ResponseEntity<List<ReplyDto>> showReply(@RequestHeader(value = "Authorization") String header,
                                                    @PathVariable int repoId, @PathVariable int prId,
                                                    @PathVariable int commentId) {
        String accessToken = header.split(" ")[1];
        List<ReplyDto> replies = prService.showReply(repoId, prId, commentId, accessToken);
        if (replies != null) return ResponseEntity.ok(replies);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{repoId}/{prId}/reply/{discussionId}")
    @Operation(summary = "대댓글 등록", description = "대댓글을 등록합니다")
    public ResponseEntity<Integer> insertReply(@RequestHeader(value = "Authorization") String header,
                                               @PathVariable String repoId, @PathVariable String prId,
                                               @PathVariable String discussionId, @RequestBody ReplyDto replyDto) {
        String accessToken = header.split(" ")[1];
        int result = prService.insertReply(repoId, prId, discussionId, replyDto, accessToken);
        if (result == 200) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{repoId}/{prId}/reply/{replyId}")
    @Operation(summary = "대댓글 수정", description = "대댓글을 수정합니다")
    public ResponseEntity<Integer> updateReply(@RequestHeader(value = "Authorization") String header,
                                               @PathVariable String repoId, @PathVariable String prId,
                                               @PathVariable String replyId, @RequestBody ReplyDto replyDto) {
        String accessToken = header.split(" ")[1];
        int result = prService.updateReply(repoId, prId, replyId, replyDto, accessToken);
        if (result == 200) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{repoId}/{prId}/reply/{reCommentId}")
    @Operation(summary = "대댓글 삭제", description = "대댓글을 삭제합니다")
    public ResponseEntity<Integer> deleteReply(@RequestHeader(value = "Authorization") String header,
                                               @PathVariable String repoId, @PathVariable String prId,
                                               @PathVariable String reCommentId) {
        String accessToken = header.split(" ")[1];
        int result = prService.deleteComment(repoId, prId, reCommentId, accessToken);
        if (result != 0) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/file")
    @Operation(summary = "파일 목록 조회(PR)", description = "PR내에 변경된 모든 파일 목록을 조회합니다")
    public ResponseEntity<List<FileDto>> showFileListByPr(@RequestHeader(value = "Authorization") String header,
                                                          @PathVariable int repoId, @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        List<FileDto> fileList = prService.showFileListByPr(repoId, prId, accessToken);
        if (fileList != null) {
            return ResponseEntity.ok(fileList);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{repoId}/{prId}/file/raw")
    @Operation(summary = "변경 된 코드 확인", description = "변경 된 파일의 전 후 코드를 조회합니다")
    public ResponseEntity<Map<String, Object>> showChangedCode(@RequestHeader(value = "Authorization") String header,
                                                               @PathVariable String repoId, @PathVariable String prId,
                                                               @RequestBody FileDto fileDto) {
        String accessToken = header.split(" ")[1];
        Map<String, Object> changedCode = prService.showChangedCode(repoId, prId, fileDto, accessToken);
        if (changedCode != null && !changedCode.isEmpty()) {
            return ResponseEntity.ok(changedCode);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}/{prId}/reviewer")
    @Operation(summary = "리뷰 참여자 조회", description = "PR에 참여한 리뷰어들을 조회합니다")
    public ResponseEntity<Map<String, Object>> getReviewer(@RequestHeader(value = "Authorization") String header,
                                                         @PathVariable String repoId, @PathVariable String prId) {
        String accessToken = header.split(" ")[1];
        List<ReviewerDto> reviewer = prService.getReviewer(repoId, prId, accessToken);
        Map<String, Object> response = new HashMap<>();
        if (reviewer != null) {
            response.put("reviewer", reviewer);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.noContent().build();
    }
}