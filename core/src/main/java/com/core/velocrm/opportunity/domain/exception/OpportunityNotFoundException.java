package com.core.velocrm.opportunity.domain.exception;

import java.util.UUID;

public class OpportunityNotFoundException extends RuntimeException {
    public OpportunityNotFoundException(UUID id) {
        super("Opportunity with ID " + id + " was not found.");
    }
}
