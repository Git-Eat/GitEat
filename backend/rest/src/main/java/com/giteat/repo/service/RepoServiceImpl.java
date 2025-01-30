package com.giteat.repo.service;

import com.giteat.repo.entity.RepositoryEntity;
import com.giteat.repo.repository.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RepoServiceImpl")
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService{

    private final RepoRepository repoRepository;

    @Override
    public List<RepositoryEntity> getRepoList() {
        return repoRepository.getRepoList();
    }

    @Override
    public RepositoryEntity findByRepoId(int repoId) {
        return repoRepository.findByRepoId(repoId);
    }

    @Override
    public RepositoryEntity insertRepo(int repoId) {
        RepositoryEntity repo = new RepositoryEntity();
        repo.setRepoId(repoId);
        return repoRepository.save(repo);
    }

    @Override
    public int deleteRepo(int repoId) {
        return repoRepository.deleteRepo(repoId);
    }
}
