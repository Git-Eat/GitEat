package com.giteat.repo.controller;

import com.giteat.repo.entity.RepositoryEntity;
import com.giteat.repo.service.RepoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repo")
@RequiredArgsConstructor
public class RepoController {

    private final RepoServiceImpl repoService;

    @GetMapping("")
    @Operation(summary = "Repo 목록 조회", description = "등록된 Repo 목록을 조회합니다")
    ResponseEntity<List<RepositoryEntity>> getRepoList(@RequestHeader(value = "Authorization") String header) {
        String accessToken = header.split(" ")[1];
        List<RepositoryEntity> repoList = repoService.getRepoList(accessToken);
        if(repoList != null) return ResponseEntity.ok(repoList);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{repoId}")
    @Operation(summary = "Repo 삭제", description = "Repo를 삭제합니다")
    ResponseEntity<Integer> deleteRepo(@PathVariable int repoId){
        int result = repoService.deleteRepo(repoId);
        if(result != 0) return ResponseEntity.ok(result);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{repoId}")
    @Operation(summary = "Repo 상세 조회", description = "Repo의 상세정보를 조회합니다")
    ResponseEntity<RepositoryEntity> findRepoById(@PathVariable int repoId){
        RepositoryEntity repo = repoService.findByRepoId(repoId);
        if(repo != null) return ResponseEntity.ok(repo);
        return ResponseEntity.noContent().build();
    }

    /**
     * 등록한 레포지토리 정보 읽어 오기
     * @param repoBody
     * @return
     */
    @PostMapping("")
    @Operation(summary="repository의 모든 데이터 읽기", description = "repository에서 모든 데이터를 가져옵니다.")
    public ResponseEntity<?> saveRepositoryData(@RequestHeader(value = "Authorization") String header,
                                                @RequestBody Map<String, String> repoBody){
        String repositoryId = repoBody.get("repoId");
        String accessToken = header.split(" ")[1];
        RepositoryEntity repository = repoService.saveRepositoryData(accessToken, repositoryId);
        System.out.println("REPOSITORY : " + repository) ;
        System.out.println("@@@@@@@@@@@@@@@@@@" + repository);
        return ResponseEntity.ok().body(repository);
    }
}
