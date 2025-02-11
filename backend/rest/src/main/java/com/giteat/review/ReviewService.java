package com.giteat.review;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;

    public Optional<AiReviewEntity> getReview(int repoId, int prId) {

        System.out.println("rest service - repoId: " + repoId + ", prId: " + prId);
        return reviewRepository.findByRepoIdAndPrId(repoId, prId);
    }
}
