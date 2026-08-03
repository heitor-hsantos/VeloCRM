package com.core.velocrm.opportunity.application.port.out;

import com.core.velocrm.opportunity.domain.event.OpportunityMovedEvent;

public interface EventPublisherPort {
    void publish(OpportunityMovedEvent event);
}
