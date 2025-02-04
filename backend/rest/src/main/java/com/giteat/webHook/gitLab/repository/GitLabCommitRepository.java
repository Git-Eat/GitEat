package com.giteat.webHook.gitLab.repository;

import com.giteat.webHook.gitLab.entity.GitLabCommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GitLabCommitRepository extends JpaRepository<GitLabCommitEntity , Long> {
}
