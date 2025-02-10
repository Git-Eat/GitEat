package com.giteat.ai.review.daemon.service;

import com.giteat.ai.dto.FileDto;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;

import java.util.List;

public interface AiReviewService {
    List<AiReviewStatusEntity> findByStatus(int status);

    boolean createAiReview(AiReviewStatusEntity statusEntity, FileDto fileDto);
}
