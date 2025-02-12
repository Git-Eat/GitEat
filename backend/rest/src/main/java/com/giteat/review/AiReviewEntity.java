package com.giteat.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_review")
@Getter
@Setter
@NoArgsConstructor // jpa가 사용할 수 있으면서 외부의 무분별한 객체 생성 통제
public class AiReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aiReviewId;

    private int repoId;
    private int prId;
    private int arStatusId;

    @Column(name = "base_sha")
    private String baseSha;

    @Column(name = "head_sha")
    private String headSha;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    private LocalDateTime createTime;

}