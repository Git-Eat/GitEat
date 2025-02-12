package com.giteat.repo.repository;

import com.giteat.repo.entity.MergeRequestEntity;
import com.giteat.repo.entity.MergeRequestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MergeRequestRepository  extends JpaRepository<MergeRequestEntity, MergeRequestId> {
    Optional<MergeRequestEntity> findById_PrId(int prId);

    // repoId와 prId로 조회하는 메서드
    @Query("SELECT m FROM MergeRequestEntity m WHERE m.id.repoId = :repoId AND m.id.prId = :prId")
    Optional<MergeRequestEntity> findByRepoIdAndPrId(@Param("repoId") int repoId, @Param("prId") int prId);

    // 복합키를 이용하여 조회
    //Optional<MergeRequestEntity> findById(MergeRequestId id);
}
