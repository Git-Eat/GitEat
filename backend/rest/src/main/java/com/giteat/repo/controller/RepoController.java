package com.giteat.repo.controller;

import com.giteat.repo.entity.RepositoryEntity;
import com.giteat.repo.service.RepoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repo")
@RequiredArgsConstructor
public class RepoController {

    private final RepoServiceImpl repoService;

    @GetMapping("")
    @Operation(summary = "Repo 목록 조회", description = "등록된 Repo 목록을 조회합니다")
    ResponseEntity<List<RepositoryEntity>> getRepoList() {
        List<RepositoryEntity> repoList = repoService.getRepoList();
        if(repoList != null) return ResponseEntity.ok(repoList);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{repoId}")
    @Operation(summary = "Repo 등록", description = "Repo를 등록합니다")
    ResponseEntity<RepositoryEntity> insertRepo(@PathVariable int repoId){
        // repo 등록시 깃랩에서 데이터 받아와서 저장하는 함수 필요 (현재는 임의 코드)
        RepositoryEntity repo = repoService.insertRepo(repoId);
        if(repo != null) return ResponseEntity.ok(repo);
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

}
