package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.FindAllOpportunitiesUseCase;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllOpportunitiesService implements FindAllOpportunitiesUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;

    public FindAllOpportunitiesService(OpportunityRepositoryPort opportunityRepositoryPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Opportunity> findAll() {
        return opportunityRepositoryPort.findAll();
    }
}
