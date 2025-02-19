package com.giteat.repo.repository;

import com.giteat.repo.entity.PrTempEntity;
import com.giteat.repo.entity.PrTempId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrTempRepository extends JpaRepository<PrTempEntity, PrTempId> {

    // 🔥 특정 repo_id에 속한 PR 리스트 조회
    List<PrTempEntity> findByIdRepoId(int repoId);

    // 🔥 특정 PR의 temp_status 값 조회
    List<PrTempEntity> findByIdPrIdAndTempStatus(int prId, int tempStatus);

    // 🔥 특정 repo_id와 pr_id에 해당하는 데이터가 존재하는지 체크
    boolean existsById(PrTempId id);
}
