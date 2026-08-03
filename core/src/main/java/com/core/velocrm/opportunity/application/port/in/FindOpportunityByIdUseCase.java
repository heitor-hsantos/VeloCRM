package com.core.velocrm.opportunity.application.port.in;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import java.util.Optional;
import java.util.UUID;

public interface FindOpportunityByIdUseCase {
    Optional<Opportunity> findById(UUID id);
}
