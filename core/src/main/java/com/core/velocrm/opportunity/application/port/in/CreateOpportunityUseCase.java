package com.core.velocrm.opportunity.application.port.in;

import com.core.velocrm.opportunity.domain.model.Opportunity;

public interface CreateOpportunityUseCase {
    Opportunity createOpportunity(Opportunity opportunity);
}
