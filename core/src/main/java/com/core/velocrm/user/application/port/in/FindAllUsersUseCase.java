package com.core.velocrm.user.application.port.in;

import com.core.velocrm.user.domain.model.User;
import java.util.List;

public interface FindAllUsersUseCase {
    List<User> findAll();
}
