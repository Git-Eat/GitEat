package com.giteat.security.oauth.repository;

import com.giteat.security.oauth.dto.OAuthToken;
import com.giteat.security.oauth.entity.OAuthTokenEntity;
import com.giteat.security.oauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Integer> {
    Optional<OAuthTokenEntity> findByUser(UserEntity userEntity);
}
