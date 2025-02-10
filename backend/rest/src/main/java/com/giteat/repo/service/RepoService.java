package com.giteat.repo.service;

import com.giteat.repo.entity.RepositoryEntity;

import java.util.List;

public interface RepoService {
    List<RepositoryEntity> getRepoList(String accessToken);
    RepositoryEntity findByRepoId(int repoId);
    RepositoryEntity insertRepo(int repoId);
    int deleteRepo(int repoId);
    RepositoryEntity saveRepositoryData(String accessToken , String repositoryId);
}
