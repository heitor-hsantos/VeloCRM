package com.core.velocrm.opportunity.infrastructure.persistence.repository;

import com.core.velocrm.opportunity.infrastructure.persistence.entity.OpportunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OpportunityJpaRepository extends JpaRepository<OpportunityEntity, UUID> {
}

