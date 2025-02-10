package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "repository_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryMemberEntity {

    @EmbeddedId
    private RepositoryMemberId id;

    @ManyToOne
    @JoinColumn(name = "repo_id", insertable = false, updatable = false)
    private RepositoryEntity repository;
}
