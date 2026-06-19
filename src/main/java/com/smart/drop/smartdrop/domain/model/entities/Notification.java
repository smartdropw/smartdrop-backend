package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Notification entity for SmartDrop platform
 */
@Entity
@Table(name = "Notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationId")
    private Integer notificationId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "Type", length = 50)
    private String type;

    @Column(name = "Message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "Channel", length = 20)
    private String channel;

    @Column(name = "SentAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime sentAt;

    @Column(name = "Status", length = 20)
    private String status;

    public Notification(Integer userId, String type, String message, String channel) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.channel = channel;
        this.status = "PENDING";
        this.sentAt = LocalDateTime.now();
    }
}

