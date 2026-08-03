package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.UpdateOpportunityUseCase;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.exception.OpportunityNotFoundException;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UpdateOpportunityService implements UpdateOpportunityUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;

    public UpdateOpportunityService(OpportunityRepositoryPort opportunityRepositoryPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
    }

    @Override
    @Transactional
    public Opportunity updateOpportunity(UUID id, Opportunity opportunityUpdates) {
        Opportunity existing = opportunityRepositoryPort.findById(id)
                .orElseThrow(() -> new OpportunityNotFoundException(id));

        Opportunity updated = new Opportunity(
                existing.getId(),
                opportunityUpdates.getTitle(),
                opportunityUpdates.getDescription(),
                opportunityUpdates.getAmount(),
                opportunityUpdates.getStage() != null ? opportunityUpdates.getStage() : existing.getStage(),
                opportunityUpdates.getCustomAttributes(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );

        return opportunityRepositoryPort.save(updated);
    }
}
