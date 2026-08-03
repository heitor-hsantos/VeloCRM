package com.core.velocrm.opportunity.application.port.in;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.domain.model.Stage;
import java.util.UUID;

public interface MoveOpportunityUseCase {
    Opportunity moveOpportunity(UUID opportunityId, Stage newStage);
}

