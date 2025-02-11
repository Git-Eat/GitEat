package com.giteat.repo.repository;

import com.giteat.repo.entity.RepositoryEntity;
import com.giteat.repo.entity.RepositoryMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryMemberRepository extends JpaRepository<RepositoryMemberEntity, Long> {
}
