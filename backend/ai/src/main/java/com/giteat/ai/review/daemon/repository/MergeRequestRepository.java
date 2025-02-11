package com.giteat.ai.review.daemon.repository;



import com.giteat.ai.review.daemon.entity.MergeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MergeRequestRepository extends JpaRepository<MergeRequestEntity, Long> {
    Optional<MergeRequestEntity> findById_PrId(int prId);
}

