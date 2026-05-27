package com.core.velocrm.opportunity.infrastructure.dto;

import com.core.velocrm.opportunity.domain.model.Stage;
import java.math.BigDecimal;
import java.util.UUID;

public record OpportunityRequest(
        String title,
        String description,
        BigDecimal amount,
        Stage stage,
        String customAttributes
) {}

