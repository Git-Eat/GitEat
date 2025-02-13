package com.giteat.ai.review.daemon.service;

import com.giteat.ai.dto.FileDto;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;

import java.util.List;
import java.util.Map;

public interface AiReviewService {
    List<AiReviewStatusEntity> findByStatus(int status);

    boolean createAiReview(AiReviewStatusEntity statusEntity, List<Map<String, Object>> diffs);
}
