package com.giteat.ai.review.daemon.repository;

import com.giteat.ai.review.daemon.entity.AiReviewEntity;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiReviewEntityRepository extends JpaRepository<AiReviewEntity, Long> {

}
