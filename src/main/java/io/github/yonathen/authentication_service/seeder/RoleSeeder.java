package io.github.yonathen.authentication_service.seeder;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.yonathen.authentication_service.model.ERole;
import io.github.yonathen.authentication_service.model.Role;
import io.github.yonathen.authentication_service.repository.RoleRepository;
import jakarta.annotation.PostConstruct;

@Component
public class RoleSeeder {
  private final RoleRepository roleRepository;

  @Autowired
  public RoleSeeder(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }
  
  @PostConstruct
  public void seedRoles() {
    Arrays.stream(ERole.values())
        .forEach(role -> {
          if (!roleRepository.existsByName(role)) {
            roleRepository.save(new Role(null, role));
          }
        });
  }
}
