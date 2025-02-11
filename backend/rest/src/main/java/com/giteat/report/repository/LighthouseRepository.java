package com.giteat.report.repository;

import com.giteat.report.entity.LighthouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LighthouseRepository extends JpaRepository<LighthouseEntity, Integer> {

    // repoId에 해당하는 최신 데이터 1개 가져오기\
    @Query("SELECT l FROM LighthouseEntity l WHERE l.repositoryId = :repoId ORDER BY l.createAt DESC")
    Page<LighthouseEntity> findLatestByRepoId(int repoId, Pageable pageable);


}
