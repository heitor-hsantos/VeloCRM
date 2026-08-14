package com.core.velocrm.user.application.service;

import com.core.velocrm.user.application.port.in.FindAllUsersUseCase;
import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllUsersService implements FindAllUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public List<User> findAll() {
        return userRepositoryPort.findAll();
    }
}
