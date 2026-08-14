package com.core.velocrm.user.application.service;

import com.core.velocrm.user.application.port.in.DeleteUserUseCase;
import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional
    public void delete(UUID id) {
        if (userRepositoryPort.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepositoryPort.deleteById(id);
    }
}
