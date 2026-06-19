package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Alert entity for SmartDrop platform
 */
@Entity
@Table(name = "Alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlertId")
    private Integer alertId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "MetricType", length = 50)
    private String metricType;

    @Column(name = "Severity", length = 20)
    private String severity;

    @Column(name = "Message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "TriggeredAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime triggeredAt;

    public Alert(Integer userId, String metricType, String severity, String message) {
        this.userId = userId;
        this.metricType = metricType;
        this.severity = severity;
        this.message = message;
        this.triggeredAt = LocalDateTime.now();
    }
}

