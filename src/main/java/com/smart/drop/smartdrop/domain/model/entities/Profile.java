package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Profile entity for SmartDrop platform
 */
@Entity
@Table(name = "Profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProfileId")
    private Integer profileId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "FullName", length = 100)
    private String fullName;

    @Column(name = "Email", length = 150)
    private String email;

    @Column(name = "Language", length = 20)
    private String language;

    @Column(name = "NotificationsEnabled")
    private Boolean notificationsEnabled;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP")
    private java.time.LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    private java.time.LocalDateTime updatedAt;

    public Profile(Integer userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.notificationsEnabled = true;
        this.createdAt = java.time.LocalDateTime.now();
    }
}

