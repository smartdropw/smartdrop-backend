package com.smart.drop.smartdrop.interfaces.rest;

import com.smart.drop.smartdrop.application.services.SubscriptionService;
import com.smart.drop.smartdrop.domain.model.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller para gestionar suscripciones en smartdrop_db
 */
@RestController
@RequestMapping("/api/v1/legacy/subscriptions")
@Profile("legacy")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestParam Integer userId,
                                                            @RequestParam Integer planId) {
        Subscription subscription = subscriptionService.createSubscription(userId, planId);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable Integer subscriptionId) {
        Optional<Subscription> subscription = subscriptionService.findById(subscriptionId);
        return subscription.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable Integer userId) {
        return ResponseEntity.ok(subscriptionService.findByUserId(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<Subscription> getActiveSubscription(@PathVariable Integer userId) {
        Optional<Subscription> subscription = subscriptionService.findActiveSubscription(userId);
        return subscription.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.findAll());
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(@PathVariable Integer subscriptionId) {
        Subscription subscription = subscriptionService.cancelSubscription(subscriptionId);
        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        }
        return ResponseEntity.notFound().build();
    }
}

