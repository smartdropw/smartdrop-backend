package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SmartDrop Role entity
 */
@Repository
public interface SmartdropRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}

