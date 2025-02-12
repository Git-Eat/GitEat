package com.giteat.report.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LighthouseResult {
    // Performance Scores (0 ~ 1)
    private double performance;
    private double accessibility;
    private double bestPractices;
    private double seo;

    // Metrics (displayValue - String)
    private String fcp;  // First Contentful Paint
    private String lcp;  // Largest Contentful Paint
    private String tbt;  // Total Blocking Time
    private String cls;  // Cumulative Layout Shift
    private String si;   // Speed Index




}