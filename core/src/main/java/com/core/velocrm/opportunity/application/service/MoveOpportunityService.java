package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.MoveOpportunityUseCase;
import com.core.velocrm.opportunity.application.port.out.EventPublisherPort;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.event.OpportunityMovedEvent;
import com.core.velocrm.opportunity.domain.exception.OpportunityNotFoundException;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.domain.model.Stage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MoveOpportunityService implements MoveOpportunityUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    public MoveOpportunityService(OpportunityRepositoryPort opportunityRepositoryPort, EventPublisherPort eventPublisherPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    @Transactional
    public Opportunity moveOpportunity(UUID opportunityId, Stage newStage) {
        Opportunity opportunity = opportunityRepositoryPort.findById(opportunityId)
                .orElseThrow(() -> new OpportunityNotFoundException(opportunityId));

        Stage previousStage = opportunity.getStage();
        opportunity.moveToStage(newStage);

        Opportunity savedOpportunity = opportunityRepositoryPort.save(opportunity);
        eventPublisherPort.publish(new OpportunityMovedEvent(savedOpportunity, previousStage));

        return savedOpportunity;
    }
}

