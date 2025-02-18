package com.giteat.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_temp")
@Getter
@Setter
@NoArgsConstructor
public class CommentTempEntity {

    @EmbeddedId
    private CommentTempId id;

    @Column(name = "temp_status")
    private int tempStatus;

}
