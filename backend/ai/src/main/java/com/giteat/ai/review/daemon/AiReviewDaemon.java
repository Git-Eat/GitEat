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

    @Scheduled(fixedRate = 60000) // 3분마다 실행
    public void aiReviewDaemon() {
        System.out.println("\n[AiReviewDaemon] 데몬 실행 시작 ===========================");

        try {
            // status가 0인 리뷰 목록 조회 (미처리 상태)
            List<AiReviewStatusEntity> statusList = aiReviewService.findByStatus(0);

            if (statusList == null || statusList.isEmpty()) {
                System.out.println("[AiReviewDaemon] 처리할 리뷰 없음");
                return;
            }

            System.out.println("[AiReviewDaemon] 처리할 리뷰 개수: " + statusList.size());

            // 각 미생성 리뷰에 대해 AI리뷰 생성 시도
            for (AiReviewStatusEntity status : statusList) {
                try {
                    System.out.println("\n[AiReviewDaemon] 리뷰 처리 시작 -------------------");
                    System.out.println("PR ID: " + status.getPrId());
                    System.out.println("Repo ID: " + status.getRepoId());

                    // 변경된 파일 목록을 가져옵니다
                    List<Map<String, Object>> diffs = gitLabApi.getMergeRequestDiffs(
                            String.valueOf(status.getRepoId()),
                            String.valueOf(status.getPrId()),
                            status.getAccessToken() // 저장된 토큰 사용
                    );

                    if (diffs == null || diffs.isEmpty()) {
                        System.out.println("[AiReviewDaemon] 변경된 파일이 없습니다.");
                        continue;
                    }

                    System.out.println("[AiReviewDaemon] 변경된 파일 수: " + diffs.size());


                    // 각 파일의 변경사항을 수집
                    // 비즈니스 로직 서비스에 위임
//                    for (Map<String, Object> diff : diffs) {
//                        String oldPath = (String) diff.get("old_path");
//                        String newPath = (String) diff.get("new_path");
//
//                        // 파일 상태 확인
//                        int fileStatus;
//                        if (Boolean.TRUE.equals(diff.get("new_file"))) {
//                            fileStatus = 1;  // 추가
//                        } else if (Boolean.TRUE.equals(diff.get("deleted_file"))) {
//                            fileStatus = 3;  // 삭제
//                        } else if (Boolean.TRUE.equals(diff.get("renamed_file"))) {
//                            fileStatus = 4;  // 이름 변경
//                        } else {
//                            fileStatus = 2;  // 수정
//                        }
//
//                        FileDto fileDto = FileDto.builder()
//                                .repoId(status.getRepoId())
//                                .prId(status.getPrId())
//                                .oldPath(oldPath)
//                                .newPath(newPath)
//                                .fileName(newPath.substring(newPath.lastIndexOf('/') + 1))
//                                .fileStatus(fileStatus)
//                                .build();

                        boolean isSuccess = aiReviewService.createAiReview(status, diffs);
                        System.out.println("[AiReviewDaemon] 리뷰 생성 " +
                                (isSuccess ? "성공" : "실패") + ": " + status.getPrId());

                } catch (Exception e) {
                    System.out.println("AI리뷰 생성실패: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("[AiReviewDaemon] 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n[AiReviewDaemon] 데몬 실행 종료 ===========================");
    }
}