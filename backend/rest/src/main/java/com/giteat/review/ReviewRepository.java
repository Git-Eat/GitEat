package com.giteat.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<AiReviewEntity, Long> {

    Optional<AiReviewEntity> findByRepoIdAndPrId(int repoId, int prId);
}
