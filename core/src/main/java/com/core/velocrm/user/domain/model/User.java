package com.core.velocrm.user.domain.model;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record User(
        UUID id,
        String email,
        String password,
        String accountId,
        Set<Role> roles
) {
}
