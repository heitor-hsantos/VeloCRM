package com.core.velocrm.opportunity.infrastructure.persistence.mapper;

import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityRequest;
import com.core.velocrm.opportunity.infrastructure.dto.OpportunityResponse;
import com.core.velocrm.opportunity.infrastructure.persistence.entity.OpportunityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, LocalDateTime.class})
public interface OpportunityMapper {

    OpportunityMapper INSTANCE = Mappers.getMapper(OpportunityMapper.class);

    Opportunity toDomain(OpportunityEntity entity);

    OpportunityEntity toEntity(Opportunity domain);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Opportunity toDomainFromRequest(OpportunityRequest request);

    OpportunityResponse toResponse(Opportunity domain);
}

