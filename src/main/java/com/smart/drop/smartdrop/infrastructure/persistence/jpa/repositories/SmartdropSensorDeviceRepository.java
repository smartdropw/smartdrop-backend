package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.SensorDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SmartDrop SensorDevice entity
 */
@Repository
public interface SmartdropSensorDeviceRepository extends JpaRepository<SensorDevice, Integer> {
    Optional<SensorDevice> findByDeviceCode(String deviceCode);
}

