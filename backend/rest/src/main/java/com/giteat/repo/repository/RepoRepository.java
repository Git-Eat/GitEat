package com.giteat.repo.repository;


import com.giteat.repo.entity.RepositoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoRepository extends JpaRepository<RepositoryEntity, Integer> {

    // Repo 목록 확인 (현재는 Oauth가 다 진행이 안된 상태라 userId 제외 하고 레포 목록 조회)
    @Query("SELECT r FROM RepositoryEntity r")
    List<RepositoryEntity> getRepoList();

    // Repo 상세 정보 확인
    RepositoryEntity findByRepoId(int repoId);


    // Repo 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM RepositoryEntity r WHERE r.repoId = :repoId")
    int deleteRepo(int repoId);

}
