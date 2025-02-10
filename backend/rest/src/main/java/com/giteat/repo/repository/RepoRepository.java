package com.giteat.repo.repository;


import com.giteat.repo.entity.RepositoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoRepository extends JpaRepository<RepositoryEntity, Integer> {

    // 내가 등록한 Repo 목록 조회
    @Query("SELECT r FROM RepositoryEntity r " +
            "JOIN RepositoryMemberEntity rm ON r.repoId = rm.id.repoId " +
            "WHERE rm.id.userId = :userId")
    List<RepositoryEntity> getRepoList(@Param("userId") int userId);

    // Repo 상세 정보 확인
    RepositoryEntity findByRepoId(int repoId);


    // Repo 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM RepositoryMemberEntity rm WHERE rm.id.repoId = :repoId AND rm.id.userId = :userId")
    int deleteRepo(@Param("repoId") int repoId, @Param("userId") int userId);

}
