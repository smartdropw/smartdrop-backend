package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SubscriptionPlan entity for SmartDrop platform
 */
@Entity
@Table(name = "subscriptionplans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanId")
    private Integer planId;

    @Column(name = "Name", length = 50)
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "Duration")
    private Integer duration;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public SubscriptionPlan(String name, String description, BigDecimal price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createdAt = LocalDateTime.now();
    }
}

