package com.giteat.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.security.dto.CustomCommentDto;
import com.giteat.security.util.ApiUtil;
import com.giteat.security.util.TypeUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pr")
@AllArgsConstructor
public class MergeRequestController {

    private final ApiUtil apiUtil;
    private final TypeUtil typeUtil;

    @GetMapping("/{repoId}")
    @Operation(summary = "PR 목록 확인", description = "외부 API를 호출하여 PR 목록을 가져옵니다.")
    public ResponseEntity<?> getPrList(@PathVariable int repoId) {
        ResponseEntity<?> response = apiUtil.getApi("/pr/" + repoId);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/{repoId}/{prId}")
    @Operation(summary = "PR 상세 정보 확인", description = "외부 API를 호출하여 특정 PR 정보를 가져옵니다.")
    public ResponseEntity<?> getPrById(@PathVariable int repoId, @PathVariable int prId) {
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId);
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/commit")
    @Operation(summary = "Commit 목록 확인", description = "외부 API를 호출하여 특정 PR의 Commit 목록을 가져옵니다.")
    public ResponseEntity<?> getCommitList(@PathVariable int repoId, @PathVariable int prId) {
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/commit");

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/commit/{commitId}")
    @Operation(summary = "Commit 상세 정보 확인", description = "외부 API를 호출하여 특정 Commit 정보를 가져옵니다.")
    public ResponseEntity<?> getCommitById(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable String commitId) {
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/" + commitId);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "PR 댓글 조회", description = "외부 API를 호출하여 특정 PR의 댓글 목록을 가져옵니다.(댓글, 대댓글, 코드댓글 포함)")
    public ResponseEntity<?> getCommentList(
            @PathVariable int repoId,
            @PathVariable int prId) {
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/comment");

        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "PR 댓글 등록", description = "외부 API를 호출하여 PR에 댓글을 등록합니다.")
    public ResponseEntity<?> insertComment(
            @PathVariable int repoId,
            @PathVariable int prId,
            @RequestBody Map<String, Object> commentDto) {

        ResponseEntity<?> request = apiUtil.postApi("/pr/" + repoId + "/" + prId + "/comment", commentDto);
        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/{repoId}/uploads")
    @Operation(summary = "파일 업로드", description = "파일 업로드 하면 markdown을 return합니다")
    public ResponseEntity<?> uploadsFile(
            @PathVariable String repoId,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided.");
        }
        try {
            // 외부 API 호출: 파일을 Multipart로 그대로 전달
            ResponseEntity<?> response = apiUtil.postApiWithFile("/pr/" + repoId + "/uploads", file);

            if (response.getBody() instanceof Map) {
                Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
                String json = new ObjectMapper().writeValueAsString(responseBody);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(json);
            }

            return ResponseEntity.badRequest().body("404");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    @PostMapping("/{repoId}/{prId}/file/comment")
    @Operation(summary = "코드에 댓글 등록", description = "외부 API를 호출하여 파일 코드에 라인별로 댓글을 등록합니다")
    public ResponseEntity<?> insertFileComment(
            @PathVariable String repoId,
            @PathVariable String prId,
            @RequestBody CustomCommentDto customCommentDto) {
        ResponseEntity<?> request = apiUtil.postApi("/pr/" + repoId + "/" + prId + "/file/comment", customCommentDto);
        return ResponseEntity.ok(request.getBody());
    }


    @PutMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "PR 댓글 수정", description = "외부 API를 호출하여 PR의 댓글을 수정합니다.")
    public ResponseEntity<?> updateComment(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable int commentId,
            @RequestBody Map<String, Object> commentDto) {
        ResponseEntity<?> request = apiUtil.putApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId, commentDto);

        return ResponseEntity.ok(request.getBody());
    }

    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "PR 댓글 삭제", description = "외부 API를 호출하여 PR의 댓글을 삭제합니다.")
    public ResponseEntity<?> deleteComment(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable int commentId) {

        ResponseEntity<?> request = apiUtil.deleteApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId, null);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/reply/{commentId}")
    @Operation(summary = "대댓글 조회", description = "외부 API를 호출하여 특정 댓글의 대댓글 목록을 가져옵니다.")
    public ResponseEntity<?> showReply(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable int commentId) {

        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId + "/reply");

        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/{repoId}/{prId}/reply/{discussionId}")
    @Operation(summary = "대댓글 등록", description = "외부 API를 호출하여 대댓글을 등록합니다.")
    public ResponseEntity<?> insertReply(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable String discussionId,
            @RequestBody Map<String, Object> replyDto) {

        ResponseEntity<?> request = apiUtil.postApi("/pr/" + repoId + "/" + prId + "/reply/" + discussionId, replyDto);

        return ResponseEntity.ok(request.getBody());
    }

    @PutMapping("/{repoId}/{prId}/reply/{replyId}")
    @Operation(summary = "대댓글 수정", description = "외부 API를 호출하여 대댓글을 수정합니다.")
    public ResponseEntity<?> updateReply(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable int replyId,
            @RequestBody Map<String, Object> replyDto) {

        ResponseEntity<?> request = apiUtil.putApi("/pr/" + repoId + "/" + prId + "/reply/" + replyId, replyDto);

        return ResponseEntity.ok(request.getBody());
    }

    @DeleteMapping("/{repoId}/{prId}/reply/{reCommentId}")
    @Operation(summary = "대댓글 삭제", description = "외부 API를 호출하여 대댓글을 삭제합니다.")
    public ResponseEntity<?> deleteReply(
            @PathVariable int repoId,
            @PathVariable int prId,
            @PathVariable int reCommentId) {

        ResponseEntity<?> request = apiUtil.deleteApi("/pr/" + repoId + "/" + prId + "/reply/" + reCommentId, null);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/file")
    @Operation(summary = "PR 내 변경된 파일 목록 조회", description = "외부 API를 호출하여 PR 내 변경된 파일 목록을 가져옵니다.")
    public ResponseEntity<?> showFileListByPr(
            @PathVariable int repoId,
            @PathVariable int prId) {

        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/file");
        return ResponseEntity.ok(request.getBody());
    }


    @PostMapping("/{repoId}/{prId}/file/raw")
    @Operation(summary = "변경된 코드 확인", description = "외부 API를 호출하여 변경된 코드 내용을 가져옵니다.")
    public ResponseEntity<?> showChangedCode(
            @PathVariable int repoId,
            @PathVariable int prId,
            @RequestBody Map<String, Object> fileDto) {

        ResponseEntity<?> response = apiUtil.postApi("/pr/" + repoId + "/" + prId + "/file/raw", fileDto);

        if (response.getBody() instanceof Map) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            try {
                return ResponseEntity.ok(responseBody);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return ResponseEntity.badRequest().body("404");
    }

    @GetMapping("/{repoId}/{prId}/reviewer")
    @Operation(summary = "리뷰 참여자 조회", description = "외부 API를 호출하여 PR에 참여한 사람 목록을 조회합니다.")
    public ResponseEntity<?> getReviewer(
            @PathVariable String repoId,
            @PathVariable String prId) {

        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/reviewer");

        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/repositoryData")
    @Operation(summary = "Repository 데이터 읽기", description = "외부 API를 호출하여 repository 데이터를 저장합니다.")
    public ResponseEntity<?> saveRepositoryData(@RequestBody String repositoryId) {

        ResponseEntity<?> request = apiUtil.postApi("/pr/repositoryData", repositoryId);

        return ResponseEntity.ok(request.getBody());
    }

}
