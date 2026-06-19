package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Consumption entity for SmartDrop platform
 */
@Entity
@Table(name = "Consumption")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConsumptionId")
    private Integer consumptionId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "VolumeUsed", precision = 10, scale = 2)
    private BigDecimal volumeUsed;

    @Column(name = "DateRecorded", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateRecorded;

    @Column(name = "UsageContext", length = 50)
    private String usageContext;

    public Consumption(Integer userId, BigDecimal volumeUsed, String usageContext) {
        this.userId = userId;
        this.volumeUsed = volumeUsed;
        this.usageContext = usageContext;
        this.dateRecorded = LocalDateTime.now();
    }
}

