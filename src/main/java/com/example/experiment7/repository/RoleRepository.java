package com.example.experiment7.repository;

import com.example.experiment7.entity.Role;
import com.example.experiment7.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
