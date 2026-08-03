package com.core.velocrm.opportunity.application.port.in;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import java.util.List;

public interface FindAllOpportunitiesUseCase {
    List<Opportunity> findAll();
}
