package com.core.velocrm.opportunity.application.service;

import com.core.velocrm.opportunity.application.port.in.CreateOpportunityUseCase;
import com.core.velocrm.opportunity.domain.model.Stage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateOpportunityService implements CreateOpportunityUseCase {




    @Override
    @Transactional
    public void createOpportunity(UUID opportunityId, Stage newStage) {

    }

}
