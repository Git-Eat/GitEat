package com.giteat.ai.review.daemon.service;

import com.giteat.ai.review.daemon.entity.AiReviewStatusEntity;
import com.giteat.ai.review.daemon.entity.OAuthEntity;
import com.giteat.ai.review.daemon.repository.AiReviewStatusRepository;
import com.giteat.ai.review.daemon.repository.RepositoryMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TokenValidationService {
    private final RepositoryMemberRepository repositoryMemberRepository;
    private final AiReviewStatusRepository aiReviewStatusRepository;

    public TokenValidationService(RepositoryMemberRepository repositoryMemberRepository, AiReviewStatusRepository aiReviewStatusRepository) {
        this.repositoryMemberRepository = repositoryMemberRepository;
        this.aiReviewStatusRepository = aiReviewStatusRepository;
    }

    public List<String> findValidAccessTokens(int repoId) {
        System.out.println("토큰 검증 시작 - Repo ID: " + repoId);
        List<OAuthEntity> tokens = repositoryMemberRepository.findAllOauthTokensByRepoId(repoId);
        List<String> validTokens = new ArrayList<>();
        // 토큰 리스트 사이즈 체크 로그 추가
        System.out.println("조회된 토큰 개수: " + (tokens != null ? tokens.size() : "null"));
        if (tokens != null) {
            System.out.println("첫 번째 토큰 정보: " + (tokens.isEmpty() ? "없음" : tokens.get(0)));
        }

        for(OAuthEntity token : tokens) {
            System.out.println("토큰 검사 - 사용자 ID: " + token.getUserId());
            System.out.println("토큰 생성 시간: " + token.getCreatedAt());
            System.out.println("토큰 만료 시간: " + token.getExpiresIn());
            if (isValidToken(token)) {
                System.out.println("유효한 토큰 발견 - 사용자 ID: " + token.getUserId());
                validTokens.add(token.getAccessToken());
//                // AI Review Status 업데이트 로직
//                AiReviewStatusEntity status = aiReviewStatusRepository.findFirstByRepoIdOrderBySendAtDesc(repoId)
//                        .orElseGet(() -> {
//                            AiReviewStatusEntity newStatus = new AiReviewStatusEntity();
//                            newStatus.setRepoId(repoId);
//                            return newStatus;
//                        });
//
//                status.setStatus(1);
//                status.setSendAt(LocalDateTime.now());
//
//                aiReviewStatusRepository.save(status);
//                System.out.println("AI Review Status DB 업데이트 완료 - Repo ID: " + repoId);
            } else {
                System.out.println("유효하지 않은 토큰 - 사용자 ID: " + token.getUserId());
            }
        }
        return validTokens;
    }

    private boolean isValidToken(OAuthEntity token) {
        if(token.getCreatedAt() == null || token.getExpiresIn() == null) {
            return false;
        }

        LocalDateTime expirationTime = token.getCreatedAt()
                .plusSeconds(token.getExpiresIn());

        LocalDateTime timeWithBuffer = LocalDateTime.now().plusMinutes(3);
        boolean isValid = timeWithBuffer.isBefore(expirationTime);
        System.out.println("토큰 만료 예정 시간: " + expirationTime);
        System.out.println("현재 시간 + 3분: " + timeWithBuffer);
        System.out.println("토큰 유효 여부: " + isValid);

        return isValid;
    }
}
