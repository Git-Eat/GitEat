package com.giteat.ai.review.daemon;

import com.giteat.ai.GitLabApi;
import com.giteat.ai.dto.FileDto;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import com.giteat.ai.review.daemon.service.AiReviewService;
import com.giteat.ai.review.daemon.service.AiReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
@AllArgsConstructor
public class AiReviewDaemon {

    private final AiReviewServiceImpl aiReviewService;
    private final GitLabApi gitLabApi;

    @Scheduled(fixedRate = 180000) // 3분마다 실행
    public void aiReviewDaemon() {
        // status가 0인 리뷰 목록 조회
        List<AiReviewStatusEntity> statusList = aiReviewService.findByStatus(0);

        // null 체크 추가
        if (statusList == null || statusList.isEmpty()) {
            System.out.println("처리할 리뷰 없음");
            return;
        }

        // 각 미생성 리뷰에 대해 AI리뷰 생성 시도
        for (AiReviewStatusEntity status : statusList) {
            try {
//                // MR의 브랜치 정보 가져오기
//                Map<String, String> branches = gitLabApi.getMergeRequestBranches(
//                        String.valueOf(status.getRepoId()),
//                        String.valueOf(status.getPrId())
//                );
//
//                if (branches == null) {
//                    System.out.println("Failed to get branch information for PR {} in repo {}" +
//                            status.getPrId()+ status.getRepoId());
//                    continue;
//                }
//
//                String sourceBranch = branches.get("source_branch");
//                String targetBranch = branches.get("target_branch");
//

                // 먼저 MR의 변경된 파일 목록을 가져옵니다
                List<Map<String, Object>> diffs = gitLabApi.getMergeRequestDiffs(
                        String.valueOf(status.getRepoId()),
                        String.valueOf(status.getPrId())
                );

                if (diffs != null && !diffs.isEmpty()) {
                    for (Map<String, Object> diff : diffs) {
                        FileDto fileDto = FileDto.builder()
                                .repoId(status.getRepoId())
                                .prId(status.getPrId())
                                .oldPath((String) diff.get("old_path"))
                                .newPath((String) diff.get("new_path"))
                                .fileStatus(2)  // 수정된 파일
                                .build();


                        boolean isSuccess = aiReviewService.createAiReview(status, fileDto);
                        if (isSuccess) {
                            System.out.println("AI리뷰 생성 성공: " + status.getPrId());
                        }
                    }
                } else {
                    System.out.println("변경된 파일이 없습니다.");
                }
            } catch(Exception e) {
                System.out.println("AI리뷰 생성실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}