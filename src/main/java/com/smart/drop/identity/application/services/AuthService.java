package com.smart.drop.identity.application.services;

import com.smart.drop.identity.domain.model.User;
import com.smart.drop.identity.domain.model.Role;
import com.smart.drop.identity.domain.model.repositories.UserRepository;
import com.smart.drop.identity.domain.model.repositories.RoleRepository;
import com.smart.drop.identity.domain.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Servicio de aplicación para autenticación utilizando inyección de dependencias.
 * Orquesta la lógica de negocio entre el dominio y la persistencia.
 */
@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Registra un nuevo usuario sin roles.
     *
     * @param fullName nombre completo del usuario
     * @param email email del usuario
     * @param passwordHash hash de la contraseña
     * @return el usuario creado y persistido
     */
    public User registerUser(String fullName, String email, String passwordHash) {
        // Valida que el email no esté registrado
        if (userRepository.findByEmail(email).isPresent()) {
            throw UserAlreadyExistsException.withEmail(email);
        }

        // Crea la entidad de dominio
        User user = User.create(fullName, email, passwordHash);

        // Persiste y retorna
        return userRepository.save(user);
    }

    /**
     * Valida las credenciales e inicia sesión para un usuario.
     *
     * @param email el email del usuario
     * @param password la contraseña en texto plano
     * @return el usuario validado
     */
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.withEmail(email));

        if (!user.getPasswordHash().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return user;
    }


    /**
     * Obtiene un usuario por ID.
     *
     * @param userId el ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<User> getUser(Integer userId) {
        return userRepository.findById(userId);
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return una lista con todos los usuarios
     */
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Obtiene un usuario por email.
     *
     * @param email el email del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Asigna un rol a un usuario.
     *
     * @param userId el ID del usuario
     * @param roleName el nombre del rol
     * @return el usuario actualizado con el rol asignado
     */
    public User assignRoleToUser(Integer userId, String roleName) {
        // Obtiene el usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.withId(userId));

        // Obtiene el rol
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> RoleNotFoundException.withName(roleName));

        // Valida que el usuario no tenga ya ese rol
        if (user.hasRole(roleName)) {
            throw UserAlreadyHasRoleException.forUserAndRole(userId, roleName);
        }

        // Añade el rol
        User updatedUser = user.addRole(role);

        // Persiste
        return userRepository.update(updatedUser);
    }

    /**
     * Revoca un rol de un usuario.
     *
     * @param userId el ID del usuario
     * @param roleName el nombre del rol
     * @return el usuario actualizado sin el rol
     */
    public User revokeRoleFromUser(Integer userId, String roleName) {
        // Obtiene el usuario
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.withId(userId));

        // Obtiene el rol
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> RoleNotFoundException.withName(roleName));

        // Valida que el usuario tenga ese rol
        if (!user.hasRole(roleName)) {
            throw new IllegalArgumentException("User does not have role: " + roleName);
        }

        // Revoca el rol
        User updatedUser = user.removeRole(role);

        // Persiste
        return userRepository.update(updatedUser);
    }

    /**
     * Verifica si un usuario tiene un rol específico.
     *
     * @param userId el ID del usuario
     * @param roleName el nombre del rol
     * @return true si el usuario tiene el rol, false en caso contrario
     */
    public boolean userHasRole(Integer userId, String roleName) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> u.hasRole(roleName)).orElse(false);
    }

    /**
     * Obtiene el total de usuarios registrados.
     *
     * @return el número total de usuarios
     */
    public long getTotalUsers() {
        return userRepository.count();
    }
}

