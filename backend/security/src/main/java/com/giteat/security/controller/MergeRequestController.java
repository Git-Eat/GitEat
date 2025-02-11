package com.giteat.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giteat.security.dto.CustomCommentDto;
import com.giteat.security.util.ApiUtil;
import com.giteat.security.util.TypeUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
    public ResponseEntity<?> getPrList(@RequestHeader(value = "Authorization") String header ,@PathVariable int repoId) {
        log.info("call PrList Method");
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> response =  apiUtil.getApi("/pr/" + repoId ,  accessToken);

        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/{repoId}/{prId}")
    @Operation(summary = "PR 상세 정보 확인", description = "외부 API를 호출하여 특정 PR 정보를 가져옵니다.")
    public ResponseEntity<?> getPrById(@RequestHeader(value = "Authorization") String header ,@PathVariable int repoId, @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/commit")
    @Operation(summary = "Commit 목록 확인", description = "외부 API를 호출하여 특정 PR의 Commit 목록을 가져옵니다.")
    public ResponseEntity<?> getCommitList(@RequestHeader(value = "Authorization") String header ,@PathVariable int repoId, @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/commit/{commitId}")
    @Operation(summary = "Commit 상세 정보 확인", description = "외부 API를 호출하여 특정 Commit 정보를 가져옵니다.")
    public ResponseEntity<?> getCommitById(@RequestHeader(value = "Authorization") String header ,
                                           @PathVariable int repoId,
                                           @PathVariable int prId,
                                           @PathVariable String commitId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/" + commitId , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "PR 댓글 조회", description = "외부 API를 호출하여 특정 PR의 댓글 목록을 가져옵니다.(댓글, 대댓글, 코드댓글 포함)")
    public ResponseEntity<?> getCommentList(@RequestHeader(value = "Authorization") String header ,
                                            @PathVariable int repoId,
                                            @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/comment" , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/{repoId}/{prId}/comment")
    @Operation(summary = "PR 댓글 등록", description = "외부 API를 호출하여 PR에 댓글을 등록합니다.")
    public ResponseEntity<?> insertComment(@RequestHeader(value = "Authorization") String header ,
                                           @PathVariable int repoId,
                                           @PathVariable int prId,
                                           @RequestBody Map<String, Object> commentDto) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request =  apiUtil.postApi("/pr/" + repoId + "/" + prId + "/comment", commentDto , accessToken);
        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/{repoId}/uploads")
    @Operation(summary = "파일 업로드", description = "파일 업로드 하면 markdown을 return합니다")
    public ResponseEntity<?> uploadsFile(@RequestHeader(value = "Authorization") String header ,
                                         @PathVariable String repoId,
                                         @RequestParam(value = "file", required = false) MultipartFile file) {
        String accessToken = header.split(" ")[1];
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided.");
        }
        try {
            // 외부 API 호출: 파일을 Multipart로 그대로 전달
            ResponseEntity<?> response = apiUtil.postApiWithFile("/pr/" + repoId + "/uploads", file , accessToken);

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
    @Operation(summary="코드에 댓글 등록", description = "외부 API를 호출하여 파일 코드에 라인별로 댓글을 등록합니다")
    public ResponseEntity<?> insertFileComment(@RequestHeader("Authorization") String header ,
                                               @PathVariable String repoId,
                                               @PathVariable String prId,
                                               @RequestBody CustomCommentDto customCommentDto){
        String accessToken = header.split(" ")[1];
       ResponseEntity<?> request =  apiUtil.postApi("/pr/" + repoId + "/" + prId + "/file/comment",customCommentDto,accessToken);
        System.out.println("CONTROLLER SUCCESS TO GET DATA");
        return ResponseEntity.ok(request.getBody());

//        Object json = typeUtil.convertJsonToObject(request.getBody());
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(json); // Integer 반환
    }


    @PutMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "PR 댓글 수정", description = "외부 API를 호출하여 PR의 댓글을 수정합니다.")
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization") String header ,
                                           @PathVariable int repoId,
                                           @PathVariable int prId,
                                           @PathVariable int commentId,
                                           @RequestBody Map<String, Object> commentDto) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.putApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId, commentDto , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @DeleteMapping("/{repoId}/{prId}/comment/{commentId}")
    @Operation(summary = "PR 댓글 삭제", description = "외부 API를 호출하여 PR의 댓글을 삭제합니다.")
    public ResponseEntity<?> deleteComment(@RequestHeader(value = "Authorization") String header ,
                                           @PathVariable int repoId,
                                           @PathVariable int prId,
                                           @PathVariable int commentId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.deleteApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId, null , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/reply/{commentId}")
    @Operation(summary = "대댓글 조회", description = "외부 API를 호출하여 특정 댓글의 대댓글 목록을 가져옵니다.")
    public ResponseEntity<?> showReply(@RequestHeader(value = "Authorization") String header ,
                                       @PathVariable int repoId,
                                       @PathVariable int prId,
                                       @PathVariable int commentId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/comment/" + commentId + "/reply" , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/{repoId}/{prId}/reply/{discussionId}")
    @Operation(summary = "대댓글 등록", description = "외부 API를 호출하여 대댓글을 등록합니다.")
    public ResponseEntity<?> insertReply(@RequestHeader(value = "Authorization") String header ,
                                         @PathVariable int repoId,
                                         @PathVariable int prId,
                                         @PathVariable int discussionId,
                                         @RequestBody Map<String, Object> replyDto) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.postApi("/pr/" + repoId + "/" + prId + "/reply/" + discussionId, replyDto , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @PutMapping("/{repoId}/{prId}/reply/{replyId}")
    @Operation(summary = "대댓글 수정", description = "외부 API를 호출하여 대댓글을 수정합니다.")
    public ResponseEntity<?> updateReply(@RequestHeader(value = "Authorization") String header ,
                                         @PathVariable int repoId,
                                         @PathVariable int prId,
                                         @PathVariable int replyId,
                                         @RequestBody Map<String, Object> replyDto) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.putApi("/pr/" + repoId + "/" + prId + "/reply/" + replyId, replyDto , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @DeleteMapping("/{repoId}/{prId}/reply/{reCommentId}")
    @Operation(summary = "대댓글 삭제", description = "외부 API를 호출하여 대댓글을 삭제합니다.")
    public ResponseEntity<?> deleteReply(@RequestHeader(value = "Authorization") String header ,
                                         @PathVariable int repoId,
                                         @PathVariable int prId,
                                         @PathVariable int reCommentId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request =apiUtil.deleteApi("/pr/" + repoId + "/" + prId + "/reply/"+ reCommentId, null , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/{prId}/file")
    @Operation(summary = "PR 내 변경된 파일 목록 조회", description = "외부 API를 호출하여 PR 내 변경된 파일 목록을 가져옵니다.")
    public ResponseEntity<?> showFileListByPr(@RequestHeader(value = "Authorization") String header ,
                                              @PathVariable int repoId,
                                              @PathVariable int prId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request =  apiUtil.getApi("/pr/" + repoId + "/" + prId + "/file" , accessToken);
        return ResponseEntity.ok(request.getBody());
    }

//    @GetMapping("/{repoId}/{prId}/file/{commitId}")
//    @Operation(summary = "Commit별 변경된 파일 목록 조회", description = "외부 API를 호출하여 특정 Commit 내 변경된 파일 목록을 가져옵니다.")
//    public ResponseEntity<?> showFileListByCommit(@PathVariable int repoId,
//                                                  @PathVariable int prId,
//                                                  @PathVariable String commitId) {
//        ResponseEntity<String> request = (ResponseEntity<String>) apiUtil.getApi("/pr/" + repoId + "/" + prId + "/file/" + commitId);
//        Object json = typeUtil.convertJsonToObject(request.getBody());
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(json);
//    }

    @PostMapping("/{repoId}/{prId}/file/raw")
    @Operation(summary = "변경된 코드 확인", description = "외부 API를 호출하여 변경된 코드 내용을 가져옵니다.")
    public ResponseEntity<?> showChangedCode(@RequestHeader(value = "Authorization") String header ,
                                             @PathVariable int repoId,
                                             @PathVariable int prId,
                                             @RequestBody Map<String, Object> fileDto) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> response = apiUtil.postApi("/pr/" + repoId + "/" + prId + "/file/raw", fileDto ,accessToken);

        if (response.getBody() instanceof Map) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            try{
                return ResponseEntity.ok(responseBody);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return ResponseEntity.badRequest().body("404");
    }

    @GetMapping("/{repoId}/{prId}/reviewer")
    @Operation(summary = "리뷰 참여자 조회", description = "외부 API를 호출하여 PR에 참여한 사람 목록을 조회합니다.")
    public ResponseEntity<?> getReviewer(@RequestHeader(value = "Authorization") String header ,
                                         @PathVariable String repoId,
                                         @PathVariable String prId){
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.getApi("/pr/" + repoId + "/" + prId + "/reviewer" , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

    @PostMapping("/repositoryData")
    @Operation(summary = "Repository 데이터 읽기", description = "외부 API를 호출하여 repository 데이터를 저장합니다.")
    public ResponseEntity<?> saveRepositoryData(@RequestHeader("Authorization") String header, @RequestBody String repositoryId) {
        String accessToken = header.split(" ")[1];
        ResponseEntity<?> request = apiUtil.postApi("/pr/repositoryData", repositoryId , accessToken);

        return ResponseEntity.ok(request.getBody());
    }

}
