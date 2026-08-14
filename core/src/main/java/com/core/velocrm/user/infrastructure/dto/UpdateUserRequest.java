package com.core.velocrm.user.infrastructure.dto;

import jakarta.validation.constraints.Email;
import java.util.Set;

public record UpdateUserRequest(
        @Email String email,
        String password,
        Set<String> roles
) {
}
