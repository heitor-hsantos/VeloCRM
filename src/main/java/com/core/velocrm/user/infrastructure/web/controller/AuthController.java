package com.core.velocrm.user.infrastructure.web.controller;

import com.core.velocrm.user.application.port.in.AuthenticateUserUseCase;
import com.core.velocrm.user.application.port.in.RegisterUserUseCase;
import com.core.velocrm.user.domain.model.User;
import com.core.velocrm.user.infrastructure.dto.AuthRequest;
import com.core.velocrm.user.infrastructure.dto.AuthResponse;
import com.core.velocrm.user.infrastructure.dto.RegisterUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserRequest request) {
        User user = registerUserUseCase.registerUser(request.email(), request.password(), request.accountId(), request.roles());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String token = authenticateUserUseCase.authenticate(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
