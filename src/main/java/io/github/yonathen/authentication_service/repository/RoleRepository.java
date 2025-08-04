package io.github.yonathen.authentication_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.yonathen.authentication_service.model.ERole;
import io.github.yonathen.authentication_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    boolean existsByName(ERole name);
}
