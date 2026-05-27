package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.MoveOpportunityUseCase;
import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.domain.model.Stage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MoveOpportunityService implements MoveOpportunityUseCase {

    private final OpportunityRepositoryPort opportunityRepositoryPort;

    public MoveOpportunityService(OpportunityRepositoryPort opportunityRepositoryPort) {
        this.opportunityRepositoryPort = opportunityRepositoryPort;
    }

    @Override
    @Transactional
    public void moveOpportunity(UUID opportunityId, Stage newStage) {
        Opportunity opportunity = opportunityRepositoryPort.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        opportunity.moveToStage(newStage);

        opportunityRepositoryPort.save(opportunity);
    }
}

