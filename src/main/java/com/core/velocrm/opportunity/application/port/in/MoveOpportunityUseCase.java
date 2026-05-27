package com.core.velocrm.opportunity.application.port.in;

import com.core.velocrm.opportunity.domain.model.Stage;
import java.util.UUID;

public interface MoveOpportunityUseCase {
    void moveOpportunity(UUID opportunityId, Stage newStage);
}

