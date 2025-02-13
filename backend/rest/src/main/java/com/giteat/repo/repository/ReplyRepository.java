package com.giteat.repo.repository;

import com.giteat.repo.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository  extends JpaRepository<ReplyEntity, Long> {
}
