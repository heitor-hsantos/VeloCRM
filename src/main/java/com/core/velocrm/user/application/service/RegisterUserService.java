package com.core.velocrm.user.application.service;

import com.core.velocrm.user.application.port.in.RegisterUserUseCase;
import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(String email, String rawPassword, Set<String> roles) {
        if (userRepositoryPort.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email already exists");
        }

        Set<Role> assignedRoles = userRepositoryPort.findRolesByNames(roles);

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .accountId(java.util.UUID.randomUUID().toString())
                .roles(assignedRoles)
                .build();

        return userRepositoryPort.save(user);
    }
}
