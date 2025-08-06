package com.barbearia.api.repository;

import com.barbearia.api.model.Role;
import com.barbearia.api.security.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
