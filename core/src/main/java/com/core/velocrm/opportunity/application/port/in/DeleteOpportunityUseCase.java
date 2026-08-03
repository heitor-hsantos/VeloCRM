package com.core.velocrm.opportunity.application.port.in;

import java.util.UUID;

public interface DeleteOpportunityUseCase {
    void deleteOpportunity(UUID id);
}
