package com.giteat.repo.repository;

import com.giteat.repo.entity.MergeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MergeRequestRepository  extends JpaRepository<MergeRequestEntity, Long> {
    Optional<MergeRequestEntity> findById_PrId(int prId);
}
