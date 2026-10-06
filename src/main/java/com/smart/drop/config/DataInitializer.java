package com.smart.drop.config;

import com.smart.drop.iam.infrastructure.persistence.jpa.entities.RoleEntity;
import com.smart.drop.iam.infrastructure.persistence.jpa.entities.UserEntity;
import com.smart.drop.iam.infrastructure.persistence.jpa.repositories.RoleJpaRepository;
import com.smart.drop.iam.infrastructure.persistence.jpa.repositories.UserJpaRepository;
import com.smart.drop.inventory.infrastructure.persistence.jpa.entities.SensorDeviceEntity;
import com.smart.drop.inventory.infrastructure.persistence.jpa.entities.TankEntity;
import com.smart.drop.inventory.infrastructure.persistence.jpa.repositories.SensorDeviceJpaRepository;
import com.smart.drop.inventory.infrastructure.persistence.jpa.repositories.TankJpaRepository;
import com.smart.drop.support.infrastructure.persistence.jpa.entities.AlertEntity;
import com.smart.drop.support.infrastructure.persistence.jpa.repositories.AlertJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Semillero de datos inicial para entorno de desarrollo local y pruebas (H2 / MySQL).
 * Garantiza datos coherentes de usuarios, tanques, sensores y alertas al arrancar la app.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserJpaRepository userRepository;
    private final RoleJpaRepository roleRepository;
    private final TankJpaRepository tankRepository;
    private final SensorDeviceJpaRepository deviceRepository;
    private final AlertJpaRepository alertRepository;

    public DataInitializer(UserJpaRepository userRepository,
                           RoleJpaRepository roleRepository,
                           TankJpaRepository tankRepository,
                           SensorDeviceJpaRepository deviceRepository,
                           AlertJpaRepository alertRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tankRepository = tankRepository;
        this.deviceRepository = deviceRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Semillero omitido: Ya existen registros de usuarios en la base de datos.");
            return;
        }

        log.info("Iniciando sembrado de datos de prueba para SmartDrop...");

        // 1. Crear Roles
        RoleEntity roleUser = roleRepository.save(new RoleEntity("ROLE_USER", "Usuario Estándar de la Plataforma"));
        RoleEntity roleAdmin = roleRepository.save(new RoleEntity("ROLE_ADMIN", "Administrador Global del Sistema"));
        RoleEntity roleBrewer = roleRepository.save(new RoleEntity("ROLE_BREWER", "Operador de Planta de Bebidas"));

        // 2. Crear Usuarios (Contraseña de prueba: 'SmartDrop2026!')
        UserEntity userDemo = new UserEntity("Juan Pérez (Residencial)", "demo@smartdrop.io", "$2a$10$wTqKx7MfvT6K.8JjF0Yykeu3W1eY8M3K8Qc9h2eL8y6v2W0z9oZ9K");
        userDemo.getRoles().add(roleUser);
        userDemo = userRepository.save(userDemo);

        UserEntity userBrewer = new UserEntity("Carlos Bohórquez (Cerveza Bohórquez)", "brewer@bohorquez.pe", "$2a$10$wTqKx7MfvT6K.8JjF0Yykeu3W1eY8M3K8Qc9h2eL8y6v2W0z9oZ9K");
        userBrewer.getRoles().add(roleBrewer);
        userBrewer = userRepository.save(userBrewer);

        UserEntity userAdmin = new UserEntity("Marco Ochante (Admin)", "admin@smartdrop.io", "$2a$10$wTqKx7MfvT6K.8JjF0Yykeu3W1eY8M3K8Qc9h2eL8y6v2W0z9oZ9K");
        userAdmin.getRoles().add(roleAdmin);
        userAdmin = userRepository.save(userAdmin);

        // 3. Crear Tanques (Residencial y Cervecero)
        TankEntity tank1 = new TankEntity();
        tank1.setUserId(userDemo.getUserId());
        tank1.setName("Cisterna Principal Residencial");
        tank1.setCapacity(1000);
        tank1.setCurrent(820);
        tankRepository.save(tank1);

        TankEntity tank2 = new TankEntity();
        tank2.setUserId(userBrewer.getUserId());
        tank2.setName("Fermentador Cónico Ale #1");
        tank2.setCapacity(500);
        tank2.setCurrent(480);
        tankRepository.save(tank2);

        TankEntity tank3 = new TankEntity();
        tank3.setUserId(userBrewer.getUserId());
        tank3.setName("Tanque de Agua Helada (HLT)");
        tank3.setCapacity(1200);
        tank3.setCurrent(950);
        tankRepository.save(tank3);

        // 4. Crear Dispositivos Sensores
        SensorDeviceEntity device1 = new SensorDeviceEntity();
        device1.setUserId(userDemo.getUserId());
        device1.setName("Sensor Ultrasónico HC-SR04 Cisterna");
        device1.setLocation("Techo / Cisterna Residencial");
        device1.setFlow("0.0 L/min");
        device1.setDaily("180 L");
        device1.setBattery(94);
        device1.setStatus("ONLINE");
        deviceRepository.save(device1);

        SensorDeviceEntity device2 = new SensorDeviceEntity();
        device2.setUserId(userBrewer.getUserId());
        device2.setName("Sonda Térmica Digital PT100 Fermentador");
        device2.setLocation("Planta Fermentación Bohórquez");
        device2.setFlow("1.2 L/min");
        device2.setDaily("480 L");
        device2.setBattery(100);
        device2.setStatus("ONLINE");
        deviceRepository.save(device2);

        // 5. Crear Alertas Demostrativas
        AlertEntity alert1 = new AlertEntity();
        alert1.setUserId(userDemo.getUserId());
        alert1.setType("NIGHT_LEAK");
        alert1.setTitle("Alerta de Fuga Nocturna en Cisterna");
        alert1.setDescription("Descenso volumétrico anómalo de 12 cm/h detectado entre las 02:00 y 04:00 AM.");
        alert1.setResolved(false);
        alert1.setCreatedAt(LocalDateTime.now().minusHours(3));
        alertRepository.save(alert1);

        AlertEntity alert2 = new AlertEntity();
        alert2.setUserId(userBrewer.getUserId());
        alert2.setType("THERMAL_EXCURSION");
        alert2.setTitle("Desviación Térmica Crítica en Lote Pale Ale");
        alert2.setDescription("Temperatura superó 21.8°C (umbral máx. 20.5°C) por más de 30 minutos continuos.");
        alert2.setResolved(false);
        alert2.setCreatedAt(LocalDateTime.now().minusMinutes(45));
        alertRepository.save(alert2);

        log.info("Semillero de datos completado exitosamente: 3 usuarios, 3 tanques, 2 sensores, 2 alertas creadas.");
    }
}
