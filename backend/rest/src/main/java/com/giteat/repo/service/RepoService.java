package com.giteat.repo.service;

import com.giteat.repo.entity.RepositoryEntity;

import java.util.List;

public interface RepoService {
    List<RepositoryEntity> getRepoList(String accessToken);
    RepositoryEntity findByRepoId(int repoId , String accessToken);
    RepositoryEntity insertRepo(int repoId , String accessToken);
    int deleteRepo(int repoId , String accessToken);
    RepositoryEntity saveRepositoryData(String accessToken , String repositoryId);

    void createWebHook(String accessToken , String repoId);
}
