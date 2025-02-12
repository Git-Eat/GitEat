package com.giteat.security.controller;

import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/statistics/repo")
@AllArgsConstructor
public class RepositoryStatisticsController {

    private final ApiUtil apiUtil;

    @GetMapping("/{repoId}/commit")
    @Operation(summary="Commit 수 조회", description = "Repo에 등록된 총 Commit 개수를 조회합니다.")
    public ResponseEntity<?> getTotalCommit(@PathVariable String repoId){
        log.info("call Commit수 조회");
        ResponseEntity<?> request = apiUtil.getApi("/statistics/repo/"+repoId+"/commit");
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/participants")
    @Operation(summary = "참여자 조회", description = "해당 Repo에서의 참여자 목록을 조회합니다.")
    public ResponseEntity<?> getParticipants(@PathVariable String repoId){
        log.info("call 참여자 조회");
        ResponseEntity<?> request = apiUtil.getApi("/statistics/repo/"+repoId+"/participants");
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/pr")
    @Operation(summary="PR 통계 (PR 개수 조회)", description = "Repo에 등록된 PR개수의 통계정보를 조회합니다.")
    public ResponseEntity<?> getTotalPR(@PathVariable String repoId){
        log.info("call PR 통계 조회");
        ResponseEntity<?> request = apiUtil.getApi("/statistics/repo/"+repoId+"/pr");
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/comment")
    @Operation(summary="댓글 통계 (댓글 수 조회)", description = "Repo에 등록된 댓글수 통계정보를 조회합니다.")
    public ResponseEntity<?> getTotalComment(@PathVariable String repoId){
        log.info("call 댓글 통계 조회");
        ResponseEntity<?> request = apiUtil.getApi("/statistics/repo/"+repoId+"/comment");
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/{repoId}/contributors")
    @Operation(summary = "Contributors 조회", description = "Repo내 Contributors 정보를 조회합니다.")
    public ResponseEntity<?> getContributors(@PathVariable String repoId){
        log.info("call Contributors 조회");
        ResponseEntity<?> request = apiUtil.getApi("/statistics/repo/"+repoId+"/contributors");
        return ResponseEntity.ok(request.getBody());
    }
}
