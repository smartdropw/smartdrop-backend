package com.smart.drop.smartdrop.application.services;

import com.smart.drop.smartdrop.domain.model.entities.Subscription;
import com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories.SmartdropSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Subscription business logic
 */
@Service
@Profile("legacy")
public class SubscriptionService {

    @Autowired
    private SmartdropSubscriptionRepository smartdropSubscriptionRepository;

    public Subscription createSubscription(Integer userId, Integer planId) {
        Subscription subscription = new Subscription(userId, planId);
        return smartdropSubscriptionRepository.save(subscription);
    }

    public List<Subscription> findByUserId(Integer userId) {
        return smartdropSubscriptionRepository.findByUserId(userId);
    }

    public Optional<Subscription> findActiveSubscription(Integer userId) {
        return smartdropSubscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    public Optional<Subscription> findById(Integer subscriptionId) {
        return smartdropSubscriptionRepository.findById(subscriptionId);
    }

    public List<Subscription> findAll() {
        return smartdropSubscriptionRepository.findAll();
    }

    public Subscription cancelSubscription(Integer subscriptionId) {
        Optional<Subscription> subscription = smartdropSubscriptionRepository.findById(subscriptionId);
        if (subscription.isPresent()) {
            Subscription s = subscription.get();
            s.setStatus("CANCELLED");
            s.setEndDate(java.time.LocalDate.now());
            return smartdropSubscriptionRepository.save(s);
        }
        return null;
    }
}

