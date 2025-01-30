package com.giteat.pr.repository;

import com.giteat.pr.entity.CommitEntity;
import com.giteat.pr.entity.PrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrRepository extends JpaRepository<PrEntity, Integer> {

    // PR 목록 확인
    List<PrEntity> findByRepoId(int repoId);

    // PR 상세 정보 확인
    @Query("SELECT p FROM PrEntity p WHERE p.repoId = :repoId AND p.prId = :prId")
    PrEntity getPrById(int repoId, int prId);

    // Commit 목록 확인
    @Query("SELECT c FROM CommitEntity c WHERE c.pr.repoId = :repoId AND c.pr.prId = :prId")
    List<CommitEntity> getCommitList(int repoId, int prId);

    // Commit 상세 정보 확인
    @Query("SELECT c FROM CommitEntity c WHERE c.pr.repoId = :repoId AND c.pr.prId = :prId AND c.id = :commitId")
    CommitEntity getCommitById(int repoId, int prId, String commitId);
}
