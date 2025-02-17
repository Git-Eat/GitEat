package com.giteat.ai.review.daemon.repository;

import com.giteat.ai.review.daemon.entity.OAuthEntity;
import com.giteat.ai.review.daemon.entity.RepositoryMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositoryMemberRepository extends JpaRepository<RepositoryMemberEntity, Long> {

    @Query("SELECT o FROM RepositoryMemberEntity rm " +
            "JOIN UserEntity u ON rm.id.userId = u.userId " +
            "JOIN OAuthEntity o ON u.userId = o.userId " +
            "JOIN AiReviewStatusEntity ars ON rm.id.repoId = ars.repoId " +
            "WHERE rm.id.repoId = ars.repoId " +
            "AND rm.id.repoId = :repoId " +
            "AND o.accessToken IS NOT NULL")
    List<OAuthEntity> findAllOauthTokensByRepoId(@Param("repoId") int repoId);




}

