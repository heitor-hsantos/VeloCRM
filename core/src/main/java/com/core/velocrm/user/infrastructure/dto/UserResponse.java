package com.core.velocrm.user.infrastructure.dto;

import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String accountId,
        Set<Role> roles
) {
    public static UserResponse fromDomain(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.id(),
                user.email(),
                user.accountId(),
                user.roles()
        );
    }
}
