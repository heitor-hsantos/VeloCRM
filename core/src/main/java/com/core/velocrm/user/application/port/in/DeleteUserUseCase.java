package com.core.velocrm.user.application.port.in;

import java.util.UUID;

public interface DeleteUserUseCase {
    void delete(UUID id);
}
