package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * AdminUser entity for SmartDrop platform
 */
@Entity
@Table(name = "adminusers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminId")
    private Integer adminId;

    @Column(name = "Username", length = 50)
    private String username;

    @Column(name = "Email", length = 150)
    private String email;

    @Column(name = "Role", length = 50)
    private String role;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public AdminUser(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }
}

