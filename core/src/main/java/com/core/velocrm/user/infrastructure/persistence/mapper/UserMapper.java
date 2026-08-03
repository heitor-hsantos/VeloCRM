package com.core.velocrm.user.infrastructure.persistence.mapper;

import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;
import com.core.velocrm.user.infrastructure.persistence.entity.RoleEntity;
import com.core.velocrm.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity entity);
    UserEntity toEntity(User domain);
    Role toDomainRole(RoleEntity entity);
    RoleEntity toEntityRole(Role domain);
}
