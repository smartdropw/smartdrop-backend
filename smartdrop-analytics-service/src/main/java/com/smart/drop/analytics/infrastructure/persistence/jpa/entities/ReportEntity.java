package com.smart.drop.analytics.infrastructure.persistence.jpa.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Reports")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportId")
    private Integer reportId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "Title", length = 100)
    private String title;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "Type", length = 50)
    private String type;

    @Column(name = "GeneratedAt")
    private LocalDateTime generatedAt;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<MetricEntity> metrics = new ArrayList<>();

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<MetricEntity> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MetricEntity> metrics) {
        this.metrics = metrics;
    }
}

