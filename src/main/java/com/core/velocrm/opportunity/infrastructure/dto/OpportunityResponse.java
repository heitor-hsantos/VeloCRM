package com.core.velocrm.opportunity.infrastructure.dto;

import com.core.velocrm.opportunity.domain.model.Stage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OpportunityResponse(
        UUID id,
        String title,
        String description,
        BigDecimal amount,
        Stage stage,
        String customAttributes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

