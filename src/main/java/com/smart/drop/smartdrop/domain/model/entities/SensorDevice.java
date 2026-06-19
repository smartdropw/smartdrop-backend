package com.smart.drop.smartdrop.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SensorDevice entity for SmartDrop platform
 */
@Entity
@Table(name = "sensordevices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SensorId")
    private Integer sensorId;

    @Column(name = "DeviceCode", length = 50)
    private String deviceCode;

    @Column(name = "Type", length = 50)
    private String type;

    @Column(name = "Location", length = 100)
    private String location;

    @Column(name = "Status", length = 20)
    private String status;

    public SensorDevice(String deviceCode, String type, String location) {
        this.deviceCode = deviceCode;
        this.type = type;
        this.location = location;
        this.status = "ACTIVE";
    }
}

