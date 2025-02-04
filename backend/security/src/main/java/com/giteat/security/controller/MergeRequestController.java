package com.giteat.security.controller;

import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pr")
@AllArgsConstructor
public class MergeRequestController {

    private final ApiUtil apiUtil;

    @GetMapping("/{repoId}")
    @Operation(summary = "PR 목록 확인", description = "외부 API를 호출하여 PR 목록을 가져옵니다.")
    public ResponseEntity<?> getPrList(@PathVariable int repoId) {
        log.info("INFO: 테스트 로그 출력");
        return apiUtil.getApi("/pr/" + repoId);
    }

    @GetMapping("/{repoId}/{prId}")
    @Operation(summary = "PR 상세 정보 확인", description = "외부 API를 호출하여 특정 PR 정보를 가져옵니다.")
    public ResponseEntity<?> getPrById(@PathVariable int repoId, @PathVariable int prId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId);
    }

    @GetMapping("/{repoId}/{prId}/commit")
    @Operation(summary = "Commit 목록 확인", description = "외부 API를 호출하여 특정 PR의 Commit 목록을 가져옵니다.")
    public ResponseEntity<?> getCommitList(@PathVariable int repoId, @PathVariable int prId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId + "/commit");
    }

    @GetMapping("/{repoId}/{prId}/commit/{commitId}")
    @Operation(summary = "Commit 상세 정보 확인", description = "외부 API를 호출하여 특정 Commit 정보를 가져옵니다.")
    public ResponseEntity<?> getCommitById(@PathVariable int repoId, @PathVariable int prId, @PathVariable String commitId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId + "/commit/" + commitId);
    }

    @GetMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "PR 댓글 조회", description = "외부 API를 호출하여 특정 PR의 댓글 목록을 가져옵니다.")
    public ResponseEntity<?> getCommentList(@PathVariable int repoId, @PathVariable int prId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId + "/comment");
    }

    @PostMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "PR 댓글 등록", description = "외부 API를 호출하여 PR에 댓글을 등록합니다.")
    public ResponseEntity<?> insertComment(@PathVariable int repoId, @PathVariable int prId, @RequestBody Map<String, Object> commentDto) {
        return apiUtil.postApi("/pr/" + repoId + "/" + prId + "/comment", commentDto);
    }

    @PutMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "PR 댓글 수정", description = "외부 API를 호출하여 PR의 댓글을 수정합니다.")
    public ResponseEntity<?> updateComment(@PathVariable int repoId, @PathVariable int prId, @PathVariable int commentId, @RequestBody Map<String, Object> commentDto) {
        return apiUtil.putApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId, commentDto);
    }

    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "PR 댓글 삭제", description = "외부 API를 호출하여 PR의 댓글을 삭제합니다.")
    public ResponseEntity<?> deleteComment(@PathVariable int repoId, @PathVariable int prId, @PathVariable int commentId) {
        return apiUtil.deleteApi("/pr/" + repoId + "/" + prId + "/comment", String.valueOf(commentId));
    }

    @GetMapping("/{repoId}/{prId}/reply/{commentId}")
    @Operation(summary = "대댓글 조회", description = "외부 API를 호출하여 특정 댓글의 대댓글 목록을 가져옵니다.")
    public ResponseEntity<?> showReply(@PathVariable int repoId, @PathVariable int prId, @PathVariable int commentId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId + "/reply");
    }

    @PostMapping("/{repoId}/{prId}/reply/{discussionId}")
    @Operation(summary = "대댓글 등록", description = "외부 API를 호출하여 대댓글을 등록합니다.")
    public ResponseEntity<?> insertReply(@PathVariable int repoId, @PathVariable int prId, @PathVariable int discussionId, @RequestBody Map<String, Object> replyDto) {
        return apiUtil.postApi("/pr/" + repoId + "/" + prId + "/reply/" + discussionId, replyDto);
    }

    @PutMapping("/{repoId}/{prId}/reply/{replyId}")
    @Operation(summary = "대댓글 수정", description = "외부 API를 호출하여 대댓글을 수정합니다.")
    public ResponseEntity<?> updateReply(@PathVariable int repoId, @PathVariable int prId, @PathVariable int replyId, @RequestBody Map<String, Object> replyDto) {
        return apiUtil.putApi("/pr/" + repoId + "/" + prId + "/reply/" + replyId, replyDto);
    }

    @DeleteMapping("/{repoId}/{prId}/reply/{replyId}")
    @Operation(summary = "대댓글 삭제", description = "외부 API를 호출하여 대댓글을 삭제합니다.")
    public ResponseEntity<?> deleteReply(@PathVariable int repoId, @PathVariable int prId, @PathVariable int replyId) {
        return apiUtil.deleteApi("/pr/" + repoId + "/" + prId + "/reply", String.valueOf(replyId));
    }

    @GetMapping("/{repoId}/{prId}/file")
    @Operation(summary = "PR 내 변경된 파일 목록 조회", description = "외부 API를 호출하여 PR 내 변경된 파일 목록을 가져옵니다.")
    public ResponseEntity<?> showFileListByPr(@PathVariable int repoId, @PathVariable int prId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId + "/file");
    }

    @GetMapping("/{repoId}/{prId}/file/{commitId}")
    @Operation(summary = "Commit별 변경된 파일 목록 조회", description = "외부 API를 호출하여 특정 Commit 내 변경된 파일 목록을 가져옵니다.")
    public ResponseEntity<?> showFileListByCommit(@PathVariable int repoId, @PathVariable int prId, @PathVariable String commitId) {
        return apiUtil.getApi("/pr/" + repoId + "/" + prId + "/file/" + commitId);
    }

    @GetMapping("/{repoId}/{prId}/file/raw")
    @Operation(summary = "변경된 코드 확인", description = "외부 API를 호출하여 변경된 코드 내용을 가져옵니다.")
    public ResponseEntity<?> showChangedCode(@PathVariable int repoId, @PathVariable int prId, @RequestParam String refType, @RequestParam String ref, @RequestBody Map<String, Object> fileDto) {
        return apiUtil.postApi("/pr/" + repoId + "/" + prId + "/file/raw?refType=" + refType + "&ref=" + ref, fileDto);
    }

    @PostMapping("/repositoryData")
    @Operation(summary = "Repository 데이터 읽기", description = "외부 API를 호출하여 repository 데이터를 저장합니다.")
    public ResponseEntity<?> saveRepositoryData(@RequestHeader("accessToken") String accessToken, @RequestBody String repositoryId) {
        return apiUtil.postApi("/pr/repositoryData", repositoryId);
    }

}
