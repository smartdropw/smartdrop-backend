package com.smart.drop.identity.interfaces.rest;

import com.smart.drop.identity.application.services.AuthService;
import com.smart.drop.identity.interfaces.rest.dto.LoginUserRequestDto;
import com.smart.drop.identity.interfaces.rest.dto.RegisterUserRequestDto;
import com.smart.drop.identity.interfaces.rest.dto.UserResponseDto;
import com.smart.drop.identity.interfaces.rest.dto.AssignRoleRequestDto;
import com.smart.drop.identity.domain.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestión de autenticación y usuarios.
 * Mapea requests HTTP a casos de uso del dominio.
 */
@RestController
@RequestMapping("/api/identity/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario.
     * POST /api/identity/auth/register
     *
     * @param request datos del usuario a registrar
     * @return ResponseEntity con el usuario registrado
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody RegisterUserRequestDto request) {
        // En una aplicación real, aquí se cifraría la contraseña (ej: BCrypt)
        User user = authService.registerUser(request.fullName(), request.email(), request.password());
        UserResponseDto response = toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Inicia sesión de un usuario.
     * POST /api/identity/auth/login
     *
     * @param request credenciales del usuario
     * @return ResponseEntity con el usuario autenticado
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginUserRequestDto request) {
        User user = authService.loginUser(request.email(), request.password());
        UserResponseDto response = toUserResponseDto(user);
        return ResponseEntity.ok(response);
    }


    /**
     * Obtiene un usuario por su ID.
     * GET /api/identity/auth/users/{userId}
     *
     * @param userId el ID del usuario
     * @return ResponseEntity con el usuario, o 404 si no existe
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Integer userId) {
        return authService.getUser(userId)
                .map(user -> ResponseEntity.ok(toUserResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los usuarios.
     * GET /api/identity/auth/users
     *
     * @return ResponseEntity con la lista de usuarios
     */
    @GetMapping("/users")
    public ResponseEntity<java.util.List<UserResponseDto>> getAllUsers() {
        java.util.List<UserResponseDto> users = authService.getAllUsers().stream()
                .map(this::toUserResponseDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtiene un usuario por su email.
     * GET /api/identity/auth/users/by-email/{email}
     *
     * @param email el email del usuario
     * @return ResponseEntity con el usuario, o 404 si no existe
     */
    @GetMapping("/users/by-email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return authService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(toUserResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Asigna un rol a un usuario.
     * POST /api/identity/auth/assign-role
     *
     * @param request datos de userId y roleName
     * @return ResponseEntity con el usuario actualizado
     */
    @PostMapping("/assign-role")
    public ResponseEntity<UserResponseDto> assignRoleToUser(@RequestBody AssignRoleRequestDto request) {
        User user = authService.assignRoleToUser(request.userId(), request.roleName());
        UserResponseDto response = toUserResponseDto(user);
        return ResponseEntity.ok(response);
    }

    /**
     * Revoca un rol de un usuario.
     * POST /api/identity/auth/revoke-role
     *
     * @param request datos de userId y roleName
     * @return ResponseEntity con el usuario actualizado
     */
    @PostMapping("/revoke-role")
    public ResponseEntity<UserResponseDto> revokeRoleFromUser(@RequestBody AssignRoleRequestDto request) {
        User user = authService.revokeRoleFromUser(request.userId(), request.roleName());
        UserResponseDto response = toUserResponseDto(user);
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica si un usuario tiene un rol específico.
     * GET /api/identity/auth/users/{userId}/has-role/{roleName}
     *
     * @param userId el ID del usuario
     * @param roleName el nombre del rol
     * @return ResponseEntity con true/false
     */
    @GetMapping("/users/{userId}/has-role/{roleName}")
    public ResponseEntity<Boolean> userHasRole(@PathVariable Integer userId, @PathVariable String roleName) {
        boolean hasRole = authService.userHasRole(userId, roleName);
        return ResponseEntity.ok(hasRole);
    }

    /**
     * Obtiene el número total de usuarios registrados.
     * GET /api/identity/auth/total-users
     *
     * @return ResponseEntity con el total
     */
    @GetMapping("/total-users")
    public ResponseEntity<Long> getTotalUsers() {
        long totalUsers = authService.getTotalUsers();
        return ResponseEntity.ok(totalUsers);
    }

    /**
     * Convierte una entidad de dominio User a DTO de respuesta.
     *
     * @param user la entidad de dominio
     * @return el DTO de respuesta
     */
    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}

