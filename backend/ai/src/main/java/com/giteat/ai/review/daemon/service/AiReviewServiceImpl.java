package com.giteat.ai.review.daemon.service;

import com.giteat.ai.GitLabApi;
import com.giteat.ai.dto.FileDto;
import com.giteat.ai.review.api.AiReviewApi;
import com.giteat.ai.review.daemon.entity.AiReviewEntity;
import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import com.giteat.ai.review.daemon.entity.MergeRequestEntity;
import com.giteat.ai.review.daemon.repository.AiReviewEntityRepository;
import com.giteat.ai.review.daemon.repository.AiReviewRepository;
import com.giteat.ai.review.daemon.repository.MergeRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AiReviewServiceImpl implements AiReviewService {

    private final AiReviewRepository aiReviewRepository;
    private final AiReviewEntityRepository aiReviewEntityRepository;
    private final MergeRequestRepository mergeRequestRepository;
    private final GitLabApi gitLabApi;
    private final AiReviewApi aiReviewApi;

    @Override
    public List<AiReviewStatusEntity> findByStatus(int status) {
        try {
            System.out.println("[findByStatus] 상태 " + status + "인 리뷰 조회 시작");
            List<AiReviewStatusEntity> result = aiReviewRepository.findByStatus(status);
            if (result != null) {
                System.out.println("[findByStatus] 조회 결과: " + result.size() + "개의 리뷰 발견");
            } else {
                System.out.println("[findByStatus] 조회 결과 없음");
            }
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.out.println("상태 조회 중 에러 발생: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private Map<String, String> getChangedCode(String repoId, int prId, FileDto fileDto) throws UnsupportedEncodingException {
        String oldPath = fileDto.getOldPath();
        String newPath = fileDto.getNewPath();
        int status = fileDto.getFileStatus();

        String encodedNewPath = URLEncoder.encode(newPath, StandardCharsets.UTF_8.name());
        String encodedOldPath = URLEncoder.encode(oldPath, StandardCharsets.UTF_8.name());

        String oldRawFile = null;
        String newRawFile = null;

        Optional<MergeRequestEntity> optionalMr = mergeRequestRepository.findById_PrId(prId);
        String base_sha = null;
        String head_sha = null;

        if (optionalMr.isPresent()) {
            MergeRequestEntity existingMr = optionalMr.get();
            System.out.println("[getChangedCode] MR 정보:");
            System.out.println("- Base SHA: " + existingMr.getBaseSha());
            System.out.println("- Head SHA: " + existingMr.getHeadSha());

            if (existingMr.getBaseSha() == null || existingMr.getHeadSha() == null) {
                System.out.println("[getChangedCode] SHA가 없어서 MR 정보를 가져옵니다.");
                Map<String, Object> mrResponse = gitLabApi.getMergeRequestsById(repoId, prId, "");
                System.out.println("- MR Response: " + mrResponse);  // API 응답 확인
                existingMr.setBaseSha((String) mrResponse.get("base_commit_sha"));
                existingMr.setHeadSha((String) mrResponse.get("head_commit_sha"));
                existingMr.setStartSha((String) mrResponse.get("start_commit_sha"));

                base_sha = existingMr.getBaseSha();
                head_sha = existingMr.getHeadSha();
                System.out.println("- Updated Base SHA: " + base_sha);
                System.out.println("- Updated Head SHA: " + head_sha);
            } else {
                base_sha = existingMr.getBaseSha();
                head_sha = existingMr.getHeadSha();
            }
            mergeRequestRepository.save(existingMr);
        }
        // 파일 상태에 따라 코드 가져오기
        if (status == 1) {
            // 파일이 추가 된 경우, fileStatus = 1
            newRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, head_sha);
        } else if (status == 2) {
            // 파일 내용이 수정된 경우, fileStatus = 2
            oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, base_sha);
            newRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, head_sha);
        } else if (status == 3) {
            // 파일이 삭제 된 경우,  fileStatus = 3
            oldRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, base_sha);
        } else if (!oldPath.equals(newPath)) {
            // 파일 경로가 수정된 경우
            oldRawFile = gitLabApi.getRawCode(repoId, encodedOldPath, base_sha);
            newRawFile = gitLabApi.getRawCode(repoId, encodedNewPath, head_sha);
        }

        Map<String, String> result = new HashMap<>();
        result.put("fileName", fileDto.getFileName());
        result.put("beforeCode", oldRawFile);
        result.put("afterCode", newRawFile);
        System.out.println("AireviewServiceImpl result" + result);
        return result;
    }


    @Override
    public boolean createAiReview(AiReviewStatusEntity statusEntity, FileDto fileDto) {
        System.out.println("\n[createAiReview] AI 리뷰 생성 시작 ===========================");
        try {
            // null 체크
            if (statusEntity == null) {
                System.out.println("[createAiReview] 오류: statusEntity가 null입니다");
                return false;
            }
            if (fileDto == null) {
                System.out.println("[createAiReview] 오류: fileDto가 null입니다");
                return false;
            }

            System.out.println("[createAiReview] 입력 파라미터:");
            System.out.println("- PR ID: " + statusEntity.getPrId());
            System.out.println("- Repo ID: " + statusEntity.getRepoId());
            System.out.println("- Status ID: " + statusEntity.getArStatusId());
            System.out.println("- File Path: " + fileDto.getNewPath());

            // GitLab에서 변경된 코드 가져오기
            System.out.println("[createAiReview] GitLab API 호출 시작");
            Map<String, String> changedCode = getChangedCode(
                    String.valueOf(statusEntity.getRepoId()),
                    Integer.parseInt(String.valueOf(statusEntity.getPrId())),
                    fileDto
            );

            if (changedCode == null || changedCode.isEmpty()) {
                System.out.println("[createAiReview] 오류: GitLab에서 코드를 가져오지 못했습니다");
                return false;
            }

            // AI 리뷰 생성
//            String reviewContent = aiReviewApi.generateReview(
//                    changedCode.get("beforeCode"),
//                    changedCode.get("afterCode")
//                    );

            // AI 리뷰 엔티티 생성 및 저장
            System.out.println("[createAiReview] 리뷰 엔티티 생성 시작");
            AiReviewEntity reviewEntity = new AiReviewEntity();
            reviewEntity.setRepoId(statusEntity.getRepoId());
            reviewEntity.setPrId(statusEntity.getPrId());
            reviewEntity.setArStatusId(statusEntity.getArStatusId());
            reviewEntity.setBefore_code(changedCode.get("beforeCode"));
            reviewEntity.setAfter_code(changedCode.get("afterCode"));
//            reviewEntity.setContent(reviewContent);
            reviewEntity.setCreateTime(LocalDateTime.now());

            aiReviewEntityRepository.save(reviewEntity);

            // 리뷰 후 상태 업데이트
            statusEntity.setStatus(1);
            aiReviewRepository.save(statusEntity);
            return true;

        }
        catch (Exception e) {
            System.out.println("[createAiReview] 심각한 오류 발생 ===========================");
            System.out.println("오류 메시지: " + e.getMessage());
            System.out.println("오류 위치: "+e.getCause());
            return false;

        }

    }
}
