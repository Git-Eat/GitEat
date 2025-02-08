package com.giteat.ai.review.daemon.service;

import com.giteat.ai.review.api.AiReviewApi;
import com.giteat.ai.review.daemon.entity.AiReviewEntity;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import com.giteat.ai.review.daemon.repository.AiReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AiReviewServiceImpl implements AiReviewService {

    private final AiReviewRepository aiReviewRepository;
    private final AiReviewApi aiReviewApi;

    @Override
    public List<AiReviewStatusEntity> findByStatus(int status) {
        return aiReviewRepository.findByStatus(status);
    }

    @Override
    public boolean createAiReview(AiReviewStatusEntity statusEntity) {
        try {
            // PR 코드 변경사항 가져오는 로직
//            String beforeCode =


///////////////////////////////////////////////////////////////////////////
            // AI 리뷰 생성
            String reviewContent = aiReviewApi.generateReview(
                    statusEntity.getPrId(),
                    statusEntity.getRepoId(),
                    beforeCode,
                    afterCode
                    );

            // AI 리뷰 엔티티 생성 및 저장
            AiReviewEntity reviewEntity = new AiReviewEntity();
            reviewEntity.setRepoId(statusEntity.getRepoId());
            reviewEntity.setPrId(statusEntity.getPrId());
            reviewEntity.setArStatusId(statusEntity.getArStatusId());
            reviewEntity.setBefore_code(beforeCode);
            reviewEntity.setAfter_code(afterCode);
            reviewEntity.setContent(reviewContent);
            reviewEntity.setCreateTime(LocalDateTime.now());

            aiReviewRepository.save(reviewEntity);

            // 리뷰 후 상태 업데이트
            statusEntity.setStatus(1);
            aiReviewRepository.save(reviewEntity);
            return true;

        }
        catch (Exception e) {
            System.out.println("AI리뷰 중 에러 발생: " + e.getMessage());
            return false;

        }
        return false;

    }
}
