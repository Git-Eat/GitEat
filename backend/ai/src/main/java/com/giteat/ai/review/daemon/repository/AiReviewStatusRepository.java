package com.giteat.ai.review.daemon.repository;

import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AiReviewStatusRepository extends JpaRepository<AiReviewStatusEntity, Integer> {
    Optional<AiReviewStatusEntity> findFirstByRepoIdOrderBySendAtDesc(Integer repoId);
}
