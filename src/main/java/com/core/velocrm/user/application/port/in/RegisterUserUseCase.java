package com.core.velocrm.user.application.port.in;

import com.core.velocrm.user.domain.model.User;
import java.util.Set;

public interface RegisterUserUseCase {
    User registerUser(String email, String rawPassword, String accountId, Set<String> roles);
}
