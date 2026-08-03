package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.FindOpportunityByIdUseCase;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindOpportunityByIdService implements FindOpportunityByIdUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;

    public FindOpportunityByIdService(OpportunityRepositoryPort opportunityRepositoryPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Opportunity> findById(UUID id) {
        return opportunityRepositoryPort.findById(id);
    }
}
