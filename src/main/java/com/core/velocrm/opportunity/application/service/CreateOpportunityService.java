package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.CreateOpportunityUseCase;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOpportunityService implements CreateOpportunityUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;

    public CreateOpportunityService(OpportunityRepositoryPort opportunityRepositoryPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
    }

    @Override
    @Transactional
    public Opportunity createOpportunity(Opportunity opportunity) {
        return opportunityRepositoryPort.save(opportunity);
    }
}
