package com.core.velocrm.opportunity.infrastructure.event;

import com.core.velocrm.opportunity.application.port.out.EventPublisherPort;
import com.core.velocrm.opportunity.domain.event.OpportunityMovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisherAdapter implements EventPublisherPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(OpportunityMovedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
