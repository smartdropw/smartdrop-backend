package com.smart.drop.smartdrop.interfaces.rest;

import com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories.SmartdropUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para probar la conexión con smartdrop_db
 */
@RestController
@RequestMapping("/api/v1/legacy/users")
@Profile("legacy")
public class UserController {

    @Autowired
    private SmartdropUserRepository smartdropUserRepository;

    /**
     * Endpoint de prueba para verificar la conexión a la BD
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long count = smartdropUserRepository.count();
        return ResponseEntity.ok(count);
    }

    /**
     * Endpoint para obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(smartdropUserRepository.findAll());
    }
}

