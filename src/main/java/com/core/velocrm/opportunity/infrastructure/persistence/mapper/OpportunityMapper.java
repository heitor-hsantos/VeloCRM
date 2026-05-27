package com.core.velocrm.opportunity.infrastructure.persistence.mapper;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityRequest;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityResponse;
import com.core.velocrm.opportunity.infrastructure.persistence.entity.OpportunityEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OpportunityMapper {

    public Opportunity toDomain(OpportunityEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Opportunity(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getStage(),
                entity.getCustomAttributes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public OpportunityEntity toEntity(Opportunity domain) {
        if (domain == null) {
            return null;
        }
        OpportunityEntity entity = new OpportunityEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setAmount(domain.getAmount());
        entity.setStage(domain.getStage());
        entity.setCustomAttributes(domain.getCustomAttributes());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    public Opportunity toDomainFromRequest(OpportunityRequest request) {
        if (request == null) {
            return null;
        }
        return new Opportunity(
                UUID.randomUUID(),
                request.title(),
                request.description(),
                request.amount(),
                request.stage(),
                request.customAttributes(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public OpportunityResponse toResponse(Opportunity domain) {
        if (domain == null) {
            return null;
        }
        return new OpportunityResponse(
                domain.getId(),
                domain.getTitle(),
                domain.getDescription(),
                domain.getAmount(),
                domain.getStage(),
                domain.getCustomAttributes(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}

