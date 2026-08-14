package com.core.velocrm.user.application.port.in;

import com.core.velocrm.user.domain.model.User;
import java.util.UUID;

public interface FindUserByIdUseCase {
    User findById(UUID id);
}
