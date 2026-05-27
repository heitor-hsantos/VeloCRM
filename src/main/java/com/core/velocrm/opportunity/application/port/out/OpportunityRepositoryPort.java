package com.core.velocrm.opportunity.application.port.out;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import java.util.Optional;
import java.util.UUID;

public interface OpportunityRepositoryPort {
    Opportunity save(Opportunity opportunity);
    Optional<Opportunity> findById(UUID id);
}

