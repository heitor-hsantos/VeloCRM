package com.core.velocrm.user.infrastructure.persistence.repository;

import com.core.velocrm.user.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    Set<RoleEntity> findByNameIn(Set<String> names);
}
