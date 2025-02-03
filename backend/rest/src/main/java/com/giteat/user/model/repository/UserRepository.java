package com.giteat.user.model.repository;

import com.giteat.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<com.giteat.user.entity.UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
}
