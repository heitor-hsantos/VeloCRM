package com.core.velocrm.user.infrastructure.web.controller;

import com.core.velocrm.user.application.port.in.DeleteUserUseCase;
import com.core.velocrm.user.application.port.in.FindAllUsersUseCase;
import com.core.velocrm.user.application.port.in.FindUserByIdUseCase;
import com.core.velocrm.user.application.port.in.UpdateUserUseCase;
import com.core.velocrm.user.application.port.in.RegisterUserUseCase;
import com.core.velocrm.user.infrastructure.dto.CreateUserRequest;
import com.core.velocrm.user.infrastructure.dto.UpdateUserRequest;
import com.core.velocrm.user.infrastructure.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        var user = registerUserUseCase.registerUser(request.email(), request.password(), request.roles());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromDomain(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(UserResponse.fromDomain(findUserByIdUseCase.findById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = findAllUsersUseCase.findAll().stream()
                .map(UserResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(UserResponse.fromDomain(updateUserUseCase.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
