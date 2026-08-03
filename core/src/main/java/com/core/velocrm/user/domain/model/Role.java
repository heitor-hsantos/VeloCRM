package com.core.velocrm.user.domain.model;

import lombok.Builder;

@Builder
public record Role(
        Long id,
        String name
) {
}
