package com.core.velocrm.opportunity.application.port.in;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import java.util.UUID;

public interface UpdateOpportunityUseCase {
    Opportunity updateOpportunity(UUID id, Opportunity opportunityUpdates);
}
