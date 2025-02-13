package com.giteat.report.Repository;

import com.giteat.report.Entity.LighthouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LighthouseRepo extends JpaRepository<LighthouseEntity, Integer> {
}
