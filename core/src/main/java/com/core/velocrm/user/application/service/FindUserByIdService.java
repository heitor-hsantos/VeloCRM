package com.core.velocrm.user.application.service;

import com.core.velocrm.user.application.port.in.FindUserByIdUseCase;
import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.exception.UserNotFoundException;
import com.core.velocrm.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserByIdService implements FindUserByIdUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User findById(UUID id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
