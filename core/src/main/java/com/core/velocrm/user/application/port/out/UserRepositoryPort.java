package com.core.velocrm.user.application.port.out;

import com.core.velocrm.user.domain.model.Role;
import com.core.velocrm.user.domain.model.User;
import java.util.Optional;
import java.util.Set;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
    Set<Role> findRolesByNames(Set<String> roleNames);
}
