package com.giteat.repo.repository;

import com.giteat.repo.entity.MergeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MergeRequestRepository  extends JpaRepository<MergeRequestEntity, Long> {
}
