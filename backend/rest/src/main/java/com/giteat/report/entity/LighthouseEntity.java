package com.giteat.report.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lighthouse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LighthouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lh_id")
    private int lhId; // Primary Key

    @Column(name = "repo_id", nullable = false)
    private int repositoryId;

    @Column(name = "branch", nullable = false, length = 100)
    private String branch;

    @Column(name = "PF", nullable = false)
    private double performance;

    @Column(name = "AB", nullable = false)
    private double accessibility;

    @Column(name = "BP", nullable = false)
    private double bestPractices;

    @Column(name = "SEO", nullable = false)
    private double seo;

    @Column(name = "FCP", nullable = false, length = 50)
    private double fcp;

    @Column(name = "LCP", nullable = false, length = 50)
    private double lcp;

    @Column(name = "TBT", nullable = false, length = 50)
    private double tbt;

    @Column(name = "CLS", nullable = false, length = 50)
    private double cls;

    @Column(name = "SI", nullable = false, length = 50)
    private double si;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}
