package com.core.velocrm.user.infrastructure.dto;

import java.util.UUID;

public record RegisterResponse(
        String message,
        UUID userId,
        String token
) {
}
