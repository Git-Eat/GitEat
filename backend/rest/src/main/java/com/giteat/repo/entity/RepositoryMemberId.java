package com.giteat.repo.entity;
import lombok.*;
import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryMemberId implements Serializable {
    private int repoId;
    private int userId;
}