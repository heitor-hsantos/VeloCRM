package com.core.velocrm.opportunity.infrastructure.dto;

import com.core.velocrm.opportunity.domain.model.Stage;
import jakarta.validation.constraints.NotNull;

public record MoveOpportunityRequest(
        @NotNull(message = "Stage is required")
        Stage stage
) {}
