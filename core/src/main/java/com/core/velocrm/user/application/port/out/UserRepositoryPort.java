package com.core.velocrm.user.application.port.out;

import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
    Set<Role> findRolesByNames(Set<String> roleNames);
}
