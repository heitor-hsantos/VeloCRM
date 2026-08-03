package com.core.velocrm.user.application.service;

import com.core.velocrm.security.JwtService;
import com.core.velocrm.user.application.port.in.AuthenticateUserUseCase;
import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserService implements AuthenticateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String authenticate(String email, String rawPassword) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.password())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtService.generateToken(user);
    }
}
