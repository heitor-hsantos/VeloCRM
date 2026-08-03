package com.core.velocrm.opportunity.domain.event;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.domain.model.Stage;

public record OpportunityMovedEvent(Opportunity opportunity, Stage previousStage) {
}
