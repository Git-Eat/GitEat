package com.giteat.repo.repository;

import com.giteat.repo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<CommentEntity,Long> {
}
