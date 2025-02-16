package com.giteat.report.repository;

import com.giteat.report.entity.LighthouseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LighthouseRepository extends JpaRepository<LighthouseEntity, Integer> {

    // repoId에 해당하는 최신 데이터 1개 가져오기
    @Query("SELECT l FROM LighthouseEntity l WHERE l.repositoryId = :repoId ORDER BY l.createAt DESC")
    Page<LighthouseEntity> findLatestByRepoId(int repoId, Pageable pageable);

    // reoId에 해당하는 최신 데이터 1개를 객체로 가져오기
    @Query("SELECT l FROM LighthouseEntity l WHERE l.repositoryId = :repoId ORDER BY l.createAt DESC")
    List<LighthouseEntity> getLatestByRepoId(@Param("repoId") int repoId, Pageable pageable);

    // repoId에 해당하는 최신 데이터 1개 삭제하기
    @Modifying
    @Query("DELETE FROM LighthouseEntity l WHERE l.lhId = :lhId")
    void deleteById(@Param("lhId") int lhId);

}
