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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class AiReviewServiceImpl implements AiReviewService {

    private final AiReviewRepository aiReviewRepository;
    private final AiReviewEntityRepository aiReviewEntityRepository;
    private final MergeRequestRepository mergeRequestRepository;
    private final GitLabApi gitLabApi;
    private final AiReviewApi aiReviewApi;
    private final TokenValidationService tokenValidationService;

    @Override
    public List<AiReviewStatusEntity> findByStatus(int status) {
        try {
            List<AiReviewStatusEntity> result = aiReviewRepository.findByStatus(status);
            log.info("[findByStatus] 상태 {}인 리뷰 조회 시작", status);
            if (result != null) {
                log.info("[findByStatus] 조회 결과: {}개의 리뷰 발견", result.size());
            } else {
                log.info("[findByStatus] 조회 결과 없음");
            }
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            log.error("상태 조회 중 에러 발생", e);
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

        Optional<MergeRequestEntity> optionalMr = mergeRequestRepository.findById_RepoIdAndId_PrId(String.valueOf(repoId), prId);
        String base_sha = null;
        String head_sha = null;

       List<String> validTokens = tokenValidationService.findValidAccessTokens(Integer.parseInt(repoId));
        if (validTokens.isEmpty()) {
            log.error("유효한 토큰을 찾을 수 없습니다. Repo ID: {}", repoId);
            return null;
        }
        String accessToken = validTokens.get(0);

        if (optionalMr.isPresent()) {
            MergeRequestEntity existingMr = optionalMr.get();
            System.out.println("[getChangedCode] MR 정보:");
            System.out.println("- Base SHA: " + existingMr.getBaseSha());
            System.out.println("- Head SHA: " + existingMr.getHeadSha());

            if (existingMr.getBaseSha() == null || existingMr.getHeadSha() == null) {
                System.out.println("[getChangedCode] SHA가 없어서 MR 정보를 가져옵니다.");
                Map<String, Object> mrResponse = gitLabApi.getMergeRequestsById(String.valueOf(repoId), prId, accessToken);
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
            newRawFile = gitLabApi.getRawCode(String.valueOf(repoId), encodedNewPath, head_sha, accessToken);
        } else if (status == 2) {
            // 파일 내용이 수정된 경우, fileStatus = 2
            oldRawFile = gitLabApi.getRawCode(String.valueOf(repoId), encodedOldPath, base_sha, accessToken);
            newRawFile = gitLabApi.getRawCode(String.valueOf(repoId), encodedNewPath, head_sha, accessToken);
        } else if (status == 3) {
            // 파일이 삭제 된 경우,  fileStatus = 3
            oldRawFile = gitLabApi.getRawCode(String.valueOf(repoId), encodedNewPath, base_sha, accessToken);
        } else if (!oldPath.equals(newPath)) {
            // 파일 경로가 수정된 경우
            oldRawFile = gitLabApi.getRawCode(String.valueOf(repoId), encodedOldPath, base_sha, accessToken);
            newRawFile = gitLabApi.getRawCode(String.valueOf(repoId), encodedNewPath, head_sha, accessToken);
        }

        Map<String, String> result = new HashMap<>();
        result.put("fileName", fileDto.getFileName());
        result.put("beforeCode", oldRawFile);
        result.put("afterCode", newRawFile);
        result.put("baseSha", base_sha);
        result.put("headSha", head_sha);

        System.out.println("AireviewServiceImpl result" + result);
        return result;
    }

    /**
     * AI 리뷰 생성 메인 메서드
     *
     * 처리 과정:
     * 1. 입력값 유효성 검사
     * 2. PR 설명 가져오기 및 처리
     * 3. 파일들을 그룹으로 나누어 처리
     * 4. 각 그룹별 코드 리뷰 생성
     * 5. 최종 리뷰 결과 저장
     *
     * @param statusEntity AI 리뷰 상태 정보
     * @param diffs 변경된 파일들의 정보
     * @return 리뷰 생성 성공 여부
     */
    @Override
    public boolean createAiReview(AiReviewStatusEntity statusEntity, List<Map<String, Object>> diffs) {
        System.out.println("\n[createAiReview] AI 리뷰 생성 시작 ===========================");
        try {
            // null 체크
            if (statusEntity == null) {
                System.out.println("[createAiReview] 오류: statusEntity가 null입니다");
                return false;
            }
            if (diffs == null) {
                System.out.println("[createAiReview] 오류: diffs 가 null입니다");
                return false;
            }
            if (diffs.isEmpty()) {
                System.out.println("[createAiReview] 오류: diffs 가 비어있습니다");
                return false;
            }
            // PR 설정 가져오기
            String prDescription = getPrDescription(statusEntity);

            System.out.println("[createAiReview] 입력 파라미터:");
            System.out.println("- PR ID: " + statusEntity.getPrId());
            System.out.println("- Repo ID: " + statusEntity.getRepoId());
            System.out.println("- Status ID: " + statusEntity.getArStatusId());
            System.out.println("- 변경된 파일 수: " + diffs.size());

            // 파일들을 3개씩 그룹으로 나누기
            List<List<Map<String, Object>>> fileGroups = new ArrayList<>();
            int groupSize = 3;
            for (int i = 0; i < diffs.size(); i += groupSize) {
                fileGroups.add(diffs.subList(i, Math.min(i + groupSize, diffs.size())));
            }

            StringBuilder finalReview = new StringBuilder();
            List<String> previousReviews = new ArrayList<>();
            String baseSha = null;
            String headSha = null;

            // 각 그룹별로 처리
            for (List<Map<String, Object>> group : fileGroups) {
                StringBuilder beforeCode = new StringBuilder();
                StringBuilder afterCode = new StringBuilder();

                for (Map<String, Object> diff : group) {
                    // 각 파일의 변경사항을 수집
                    String oldPath = (String) diff.get("old_path");
                    String newPath = (String) diff.get("new_path");

                    // 파일 상태 확인
                    int fileStatus;
                    if (Boolean.TRUE.equals(diff.get("new_file"))) {
                        fileStatus = 1;  // 추가
                    } else if (Boolean.TRUE.equals(diff.get("deleted_file"))) {
                        fileStatus = 3;  // 삭제
                    } else if (Boolean.TRUE.equals(diff.get("renamed_file"))) {
                        fileStatus = 4;  // 이름 변경
                    } else {
                        fileStatus = 2;  // 수정
                    }

                    FileDto fileDto = FileDto.builder()
                            .repoId(statusEntity.getRepoId())
                            .prId(statusEntity.getPrId())
                            .oldPath(oldPath)
                            .newPath(newPath)
                            .fileName(newPath.substring(newPath.lastIndexOf('/') + 1))
                            .fileStatus(fileStatus)
                            .build();

                    // GitLab에서 변경된 코드 가져오기
                    Map<String, String> changedCode = getChangedCode(
                            String.valueOf(statusEntity.getRepoId()),
                            statusEntity.getPrId(),
                            fileDto
                    );

                    if (changedCode != null) {
                        // SHA 정보 저장 (처음 한 번만)
                        if (baseSha == null) {
                            baseSha = changedCode.get("baseSha");
                            headSha = changedCode.get("headSha");
                        }

                        // 변경된 코드 수집
                        beforeCode.append("\n=== ").append(fileDto.getFileName()).append(" ===\n")
                                .append(changedCode.get("beforeCode") != null ? changedCode.get("beforeCode") : "")
                                .append("\n");

                        afterCode.append("\n=== ").append(fileDto.getFileName()).append(" ===\n")
                                .append(changedCode.get("afterCode") != null ? changedCode.get("afterCode") : "")
                                .append("\n");
                    }
                }

                // 현재 그룹의 코드에 대한 리뷰 생성
                String groupReview = aiReviewApi.generateReview(
                        beforeCode.toString(),
                        afterCode.toString(),
                        previousReviews,
                        prDescription
                );

                // 리뷰 결과가 유효한 경우에만 추가
                if (groupReview != null && !groupReview.startsWith("GPT call failed")) {
                    finalReview.append(groupReview).append("\n\n");
                }
            }

            // 최종 리뷰가 있는 경우에만 저장
            if (finalReview.length() > 0) {
                // AI 리뷰 엔티티 생성 및 저장
                System.out.println("[createAiReview] 리뷰 엔티티 생성 시작");
                AiReviewEntity reviewEntity = new AiReviewEntity();
                reviewEntity.setRepoId(statusEntity.getRepoId());
                reviewEntity.setPrId(statusEntity.getPrId());
                reviewEntity.setArStatusId(statusEntity.getArStatusId());
                reviewEntity.setBaseSha(baseSha);
                reviewEntity.setHeadSha(headSha);
                reviewEntity.setContent(finalReview.toString());
                reviewEntity.setCreateTime(LocalDateTime.now());

                System.out.println("serviceImpl reviewEntity" + reviewEntity);
                aiReviewEntityRepository.save(reviewEntity);

                // 리뷰 후 상태 업데이트
                statusEntity.setStatus(1);
                aiReviewRepository.save(statusEntity);
                return true;
            }

            return false;

        } catch (Exception e) {
            System.out.println("[createAiReview] 심각한 오류 발생 ===========================");
            System.out.println("오류 메시지: " + e.getMessage());
            System.out.println("오류 위치: " + e.getCause());
            return false;
        }
    }

    /**
     * PR의 설명을 가져오는 메서드
     * 1. PR을 찾고
     * 2. description이 있는지 확인하고
     * 3. 결과를 반환
     */
    public String getPrDescription(AiReviewStatusEntity statusEntity) {
        // 1. PR 찾기
        Optional<MergeRequestEntity> optionalMr = mergeRequestRepository.findById_RepoIdAndId_PrId(
                String.valueOf(statusEntity.getRepoId()), statusEntity.getPrId()
        );

        // 2. PR이 존재하면 description 가져오기
        if(optionalMr.isPresent()) {
            MergeRequestEntity existingMr = optionalMr.get();
            String description = existingMr.getDescription();

            // 3. description이 비어있지 않은지 확인
            if(description != null && !description.trim().isEmpty()) {
                return description;
            }
        }
        return "PR 설명이 없습니다.";

    }
}