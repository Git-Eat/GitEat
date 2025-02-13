package com.giteat.ai.review.daemon.repository;

import com.giteat.ai.review.daemon.entity.AiReviewEntity;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiReviewRepository extends JpaRepository<AiReviewStatusEntity, Long> {
   List<AiReviewStatusEntity> findByStatus(int status);
   // access_token을 조회하기 위한 메서드
   Optional<AiReviewStatusEntity> findByRepoIdAndPrId(int repoId, int prId);
}
