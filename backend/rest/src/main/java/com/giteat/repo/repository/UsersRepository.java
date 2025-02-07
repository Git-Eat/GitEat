package com.giteat.repo.repository;

import com.giteat.repo.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByUserId(int userId);
}
