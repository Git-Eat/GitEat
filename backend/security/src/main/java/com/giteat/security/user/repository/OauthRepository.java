package com.giteat.security.user.repository;

import com.giteat.security.user.dto.OAuthTokenDto;
import com.giteat.security.user.entity.OauthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface OauthRepository extends JpaRepository<OauthEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE OauthEntity o " +
            "SET o.accessToken = :#{#dto.accessToken}, " +
            "o.refreshToken = :#{#dto.refreshToken}, " +
            "o.tokenType = :#{#dto.tokenType}, " +
            "o.expiresIn = :#{#dto.expiresIn}, " +
            "o.createdAt = :#{#dto.createdAt} " +  // 마지막 ',' 제거
            "WHERE o.id = :#{#dto.id}")
    int updateToken(@Param("dto") OAuthTokenDto dto); // DTO 파라미터 바인딩

}
