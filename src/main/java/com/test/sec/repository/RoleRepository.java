package com.test.sec.repository;

import com.test.sec.model.AppRole;
import com.test.sec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role , Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
