package com.core.velocrm.opportunity.infrastructure.dto;

import com.core.velocrm.opportunity.domain.model.Stage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record OpportunityRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must be less than 100 characters")
        String title,

        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,

        @NotNull(message = "Amount is required")
        @PositiveOrZero(message = "Amount must be zero or positive")
        BigDecimal amount,

        @NotNull(message = "Stage is required")
        Stage stage,

        String customAttributes
) {}
