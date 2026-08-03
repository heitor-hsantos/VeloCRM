package com.core.velocrm.opportunity.infrastructure.dto;

import com.core.velocrm.opportunity.domain.model.Stage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;

public record OpportunityResponse(
        UUID id,
        String title,
        String description,
        BigDecimal amount,
        Stage stage,
        Map<String, Object> customAttributes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

