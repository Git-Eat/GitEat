package com.giteat.ai.review.daemon.repository;

import com.giteat.ai.review.daemon.entity.AiReviewEntity;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AiReviewRepository {
    public List<AiReviewStatusEntity> findByStatus(int status) {
        return null;
    }

    public void save(AiReviewEntity reviewEntity) {

    }
}
