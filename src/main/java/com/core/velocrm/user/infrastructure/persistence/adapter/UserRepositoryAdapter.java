package com.core.velocrm.user.infrastructure.persistence.adapter;

import com.core.velocrm.user.application.port.out.UserRepositoryPort;
import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;
import com.core.velocrm.user.infrastructure.persistence.entity.RoleEntity;
import com.core.velocrm.user.infrastructure.persistence.mapper.UserMapper;
import com.core.velocrm.user.infrastructure.persistence.repository.RoleJpaRepository;
import com.core.velocrm.user.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        var entity = mapper.toEntity(user);
        var saved = userJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Set<Role> findRolesByNames(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
             return Set.of();
        }
        Set<RoleEntity> entities = roleJpaRepository.findByNameIn(roleNames);
        return entities.stream().map(mapper::toDomainRole).collect(Collectors.toSet());
    }
}
