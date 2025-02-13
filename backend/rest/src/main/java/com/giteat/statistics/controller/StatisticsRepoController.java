package com.giteat.statistics.controller;

import com.giteat.statistics.dto.CommentTotalDto;
import com.giteat.statistics.dto.ContributorsDto;
import com.giteat.statistics.dto.MergeRequestTotalDto;
import com.giteat.statistics.dto.ParticipantsDto;
import com.giteat.statistics.service.StatisticsRepoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics/repo")
public class StatisticsRepoController {

    @Autowired
    @Qualifier("StatisticsRepoServiceImpl")
    private StatisticsRepoServiceImpl statisticsRepoService;

    @GetMapping("/{repoId}/commit")
    @Operation(summary="Commit 수 조회", description = "Repo에 등록된 총 Commit 개수를 조회합니다.")
    public ResponseEntity<Map<String, Integer>> getTotalCommit(@PathVariable String repoId){
        int totalCommit = statisticsRepoService.getTotalCommit(repoId);
        Map<String, Integer> response = new HashMap<>();
        response.put("total_commit",totalCommit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{repoId}/participants")
    @Operation(summary = "참여자 조회", description = "해당 Repo에서의 참여자 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getParticipants(@PathVariable String repoId){
        List<ParticipantsDto> participants = statisticsRepoService.getParticipants(repoId);
        Map<String, Object> response = new HashMap<>();
        response.put("participants", participants);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{repoId}/pr")
    @Operation(summary="PR 통계 (PR 개수 조회)", description = "Repo에 등록된 PR개수의 통계정보를 조회합니다.")
    public ResponseEntity<MergeRequestTotalDto> getTotalPR(@PathVariable String repoId){
        MergeRequestTotalDto mergeRequestTotalDto = statisticsRepoService.getMergeReqeustTotal(repoId);
        return ResponseEntity.ok(mergeRequestTotalDto);
    }

    @GetMapping("/{repoId}/comment")
    @Operation(summary="댓글 통계 (댓글 수 조회)", description = "Repo에 등록된 댓글수 통계정보를 조회합니다.")
    public ResponseEntity<CommentTotalDto> getTotalComment(@PathVariable String repoId){
         CommentTotalDto commentTotalDto = statisticsRepoService.getCommentTotal(repoId);
         return ResponseEntity.ok(commentTotalDto);
    }

    @GetMapping("/{repoId}/contributors")
    @Operation(summary = "Contributors 조회", description = "Repo내 Contributors 정보를 조회합니다.")
    public ResponseEntity<Map<String ,Object>> getContributors(@PathVariable String repoId){
        List<ContributorsDto> contributors = statisticsRepoService.getContributors(repoId);
        Map<String ,Object> response = new HashMap<>();
        response.put("contributors",contributors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{repoId}/languages")
    @Operation(summary = "사용언어 비율 조회", description = "Repo내 사용 된 언어 비율 정보를 조회합니다.")
    public ResponseEntity<Map<String, Object>> getLanguages(@RequestHeader(value = "Authorization") String header,
                                                            @PathVariable String repoId){
        String accessToken = header.split(" ")[1];
        Map<String, Object> languages = statisticsRepoService.getLanguages(repoId, accessToken);
        return ResponseEntity.ok(languages);
    }
}
