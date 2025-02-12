package com.giteat.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<AiReviewEntity, Long> {

    List<AiReviewEntity> findByRepoIdAndPrId(int repoId, int prId);
}
