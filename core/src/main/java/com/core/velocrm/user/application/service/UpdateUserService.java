package com.core.velocrm.user.application.service;

import com.core.velocrm.user.application.port.in.UpdateUserUseCase;
import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.exception.UserNotFoundException;
import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;
import com.core.velocrm.user.infrastructure.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User update(UUID id, UpdateUserRequest request) {
        User existingUser = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        String email = existingUser.email();
        if (request.email() != null && !request.email().isBlank() && !request.email().equals(existingUser.email())) {
            if (userRepositoryPort.findByEmail(request.email()).isPresent()) {
                throw new IllegalArgumentException("User with email already exists");
            }
            email = request.email();
        }

        String password = existingUser.password();
        if (request.password() != null && !request.password().isBlank()) {
            password = passwordEncoder.encode(request.password());
        }

        Set<Role> roles = existingUser.roles();
        if (request.roles() != null && !request.roles().isEmpty()) {
            roles = userRepositoryPort.findRolesByNames(request.roles());
        }

        User updatedUser = User.builder()
                .id(existingUser.id())
                .email(email)
                .password(password)
                .accountId(existingUser.accountId())
                .roles(roles)
                .build();

        return userRepositoryPort.save(updatedUser);
    }
}
