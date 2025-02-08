package com.giteat.ai.review.daemon;

import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import com.giteat.ai.review.daemon.service.AiReviewService;
import com.giteat.ai.review.daemon.service.AiReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@AllArgsConstructor
public class AiReviewDaemon {

    private final AiReviewService aiReviewService;

    @Scheduled(fixedRate = 180000) // 3분마다 실행
    public void aiReviewDaemon() {
        // status가 0인 리뷰 목록 조회
        List<AiReviewStatusEntity> statusList = aiReviewService.findByStatus(0);

        // 각 미생성 리뷰에 대해 AI리뷰 생성 시도
        for(AiReviewStatusEntity status : statusList) {
            try {
                boolean isSuccess = aiReviewService.createAiReview(status);
                if(isSuccess) {
                    System.out.println("AI리뷰 생성 성공: " + status.getPrId());
                }
            }
            catch(Exception e) {
                System.out.println("AI리뷰 생성실패: " + e.getMessage());
            }
        }

    }
}

