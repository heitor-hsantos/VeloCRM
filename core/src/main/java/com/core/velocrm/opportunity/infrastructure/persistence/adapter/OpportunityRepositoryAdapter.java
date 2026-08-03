package com.core.velocrm.opportunity.infrastructure.persistence.adapter;

import com.core.velocrm.opportunity.application.port.out.OpportunityRepositoryPort;
import com.core.velocrm.opportunity.domain.model.Opportunity;
import com.core.velocrm.opportunity.infrastructure.persistence.mapper.OpportunityMapper;
import com.core.velocrm.opportunity.infrastructure.persistence.repository.OpportunityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OpportunityRepositoryAdapter implements OpportunityRepositoryPort {

    private final OpportunityJpaRepository jpaRepository;
    private final OpportunityMapper mapper;

    @Override
    public Opportunity save(Opportunity opportunity) {
        var entity = mapper.toEntity(opportunity);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Opportunity> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Opportunity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}

