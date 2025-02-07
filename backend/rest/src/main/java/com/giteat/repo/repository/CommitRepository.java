package com.giteat.repo.repository;

import com.giteat.repo.entity.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<CommitEntity, Long> {
}
