package com.giteat.ai.review.daemon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ai_review_status")
public class AiReviewStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int arStatusId;

    private int prId;
    private int repoId;
    private LocalDateTime sendAt;
    private int status;

    @OneToOne
    @JoinColumn(name = "ar_status_id")
    private AiReviewEntity aiReview;


}
