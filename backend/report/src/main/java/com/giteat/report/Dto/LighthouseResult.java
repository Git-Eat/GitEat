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
    private double fcp;  // First Contentful Paint
    private double lcp;  // Largest Contentful Paint
    private double tbt;  // Total Blocking Time
    private double cls;  // Cumulative Layout Shift
    private double si;   // Speed Index




}