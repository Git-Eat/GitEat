package com.giteat.user.repository;

import com.giteat.user.entity.OAuthEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;


public interface OAuthRepository extends JpaRepository<OAuthEntity, Long> {

    Optional<OAuthEntity> findByEmail(String email);
    Optional<OAuthEntity> findByRefreshToken(String refreshToken);

    //갱신 토큰 DB 저장
    @Modifying
    @Query("UPDATE OAuthEntity o " +
            "SET o.accessToken = :accessToken, " +
            "   o.tokenType = :tokenType, " +
            "   o.refreshToken = :refreshToken, " +
            "   o.expiresIn = :expiresIn, " +
            "   o.createdAt = :createAt " +
            "WHERE o.email = :email"
    )
    void updateTokens(@Param("email") String email,
                      @Param("accessToken") String accessToken,
                      @Param("tokenType") String tokenType,
                      @Param("refreshToken") String refreshToken,
                      @Param("expiresIn") Integer expiresIn,
                      @Param("createdAt") LocalDateTime createdAt
    );
}
