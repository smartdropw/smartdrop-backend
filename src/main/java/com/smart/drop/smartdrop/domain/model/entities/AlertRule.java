package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * AlertRule entity for SmartDrop platform
 */
@Entity
@Table(name = "AlertRules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RuleId")
    private Integer ruleId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "MetricType", length = 50)
    private String metricType;

    @Column(name = "MinThreshold", precision = 10, scale = 2)
    private BigDecimal minThreshold;

    @Column(name = "MaxThreshold", precision = 10, scale = 2)
    private BigDecimal maxThreshold;

    @Column(name = "Enabled")
    private Boolean enabled;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public AlertRule(Integer userId, String metricType, BigDecimal minThreshold, BigDecimal maxThreshold) {
        this.userId = userId;
        this.metricType = metricType;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.enabled = true;
        this.createdAt = LocalDateTime.now();
    }
}

