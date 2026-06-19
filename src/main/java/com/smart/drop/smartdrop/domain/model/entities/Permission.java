package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Permission entity for SmartDrop platform
 */
@Entity
@Table(name = "Permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PermissionId")
    private Integer permissionId;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "Description", length = 255)
    private String description;

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

