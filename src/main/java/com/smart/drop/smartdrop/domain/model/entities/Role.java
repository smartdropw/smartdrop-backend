package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Role entity for SmartDrop platform
 */
@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private Integer roleId;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "Description", length = 255)
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

