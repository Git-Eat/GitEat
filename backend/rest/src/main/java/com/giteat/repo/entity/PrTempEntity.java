package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pr_temp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrTempEntity {

    @EmbeddedId
    private PrTempId id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "temp_status", nullable = false)
    private int tempStatus;

}
