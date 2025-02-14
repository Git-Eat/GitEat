package com.giteat.ai.review.daemon.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
