package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Subscription entity for SmartDrop platform
 */
@Entity
@Table(name = "Subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubscriptionId")
    private Integer subscriptionId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "PlanId")
    private Integer planId;

    @Column(name = "Status", length = 20)
    private String status;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "AutoRenew")
    private Boolean autoRenew;

    public Subscription(Integer userId, Integer planId) {
        this.userId = userId;
        this.planId = planId;
        this.status = "ACTIVE";
        this.startDate = LocalDate.now();
        this.autoRenew = true;
    }
}

