package com.smart.drop.iam.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad de dominio pura para Usuario.
 * Representa un usuario del sistema sin anotaciones JPA o Spring.
 */
public class User {
    private final Integer id;
    private final String fullName;
    private final String email;
    private final String passwordHash;
    private final Set<Role> roles;
    private final LocalDateTime createdAt;

    private User(Integer id, String fullName, String email, String passwordHash, Set<Role> roles, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles != null ? Collections.unmodifiableSet(new HashSet<>(roles)) : Collections.emptySet();
        this.createdAt = createdAt;
    }

    // Factory method para crear un nuevo usuario
    public static User create(String fullName, String email, String passwordHash) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be null or blank");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("passwordHash cannot be null or blank");
        }
        return new User(null, fullName, email, passwordHash, new HashSet<>(), LocalDateTime.now());
    }

    // Factory method para crear un usuario desde BD (con ID)
    public static User createFromDatabase(Integer id, String fullName, String email, String passwordHash, Set<Role> roles, LocalDateTime createdAt) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be a positive integer");
        }
        return new User(id, fullName, email, passwordHash, roles, createdAt);
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Methods de negocio
    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(r -> r.getName().equals(roleName));
    }

    public User addRole(Role role) {
        Set<Role> newRoles = new HashSet<>(this.roles);
        newRoles.add(role);
        return new User(this.id, this.fullName, this.email, this.passwordHash, newRoles, this.createdAt);
    }

    public User removeRole(Role role) {
        Set<Role> newRoles = new HashSet<>(this.roles);
        newRoles.remove(role);
        return new User(this.id, this.fullName, this.email, this.passwordHash, newRoles, this.createdAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", rolesCount=" + roles.size() +
                ", createdAt=" + createdAt +
                '}';
    }
}

