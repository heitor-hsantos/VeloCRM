package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.DeleteOpportunityUseCase;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.exception.OpportunityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteOpportunityService implements DeleteOpportunityUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;

    public DeleteOpportunityService(OpportunityRepositoryPort opportunityRepositoryPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
    }

    @Override
    @Transactional
    public void deleteOpportunity(UUID id) {
        if (!opportunityRepositoryPort.findById(id).isPresent()) {
            throw new OpportunityNotFoundException(id);
        }
        opportunityRepositoryPort.deleteById(id);
    }
}
