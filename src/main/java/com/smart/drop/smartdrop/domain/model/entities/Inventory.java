package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Inventory entity for SmartDrop platform
 */
@Entity
@Table(name = "Inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InventoryId")
    private Integer inventoryId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "AvailableLiters", precision = 10, scale = 2)
    private BigDecimal availableLiters;

    @Column(name = "LastRefillDate")
    private java.time.LocalDate lastRefillDate;

    @Column(name = "NextRefillPrediction")
    private java.time.LocalDate nextRefillPrediction;

    public Inventory(Integer userId, BigDecimal availableLiters) {
        this.userId = userId;
        this.availableLiters = availableLiters;
        this.lastRefillDate = java.time.LocalDate.now();
    }
}

