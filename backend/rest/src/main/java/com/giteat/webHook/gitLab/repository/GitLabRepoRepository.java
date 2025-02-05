package com.giteat.webHook.gitLab.repository;

import com.giteat.webHook.gitLab.entity.GitLabRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitLabRepoRepository extends JpaRepository<GitLabRepositoryEntity , Long> {
}
