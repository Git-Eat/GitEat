package com.giteat.user.repository;

import com.giteat.security.oauth.entity.OAuthTokenEntity;
import com.giteat.security.oauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthTokenEntity, Integer> {
    Optional<OAuthTokenEntity> findByUser(UserEntity userEntity);
}
