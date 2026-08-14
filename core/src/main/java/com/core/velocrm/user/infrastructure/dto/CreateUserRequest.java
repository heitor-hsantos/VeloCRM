package com.core.velocrm.user.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record CreateUserRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        Set<String> roles
) {
}
