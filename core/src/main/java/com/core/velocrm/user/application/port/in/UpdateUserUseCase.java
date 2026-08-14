package com.core.velocrm.user.application.port.in;

import com.core.velocrm.user.domain.model.User;
import com.core.velocrm.user.infrastructure.dto.UpdateUserRequest;
import java.util.UUID;

public interface UpdateUserUseCase {
    User update(UUID id, UpdateUserRequest request);
}
