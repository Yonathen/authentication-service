package io.github.yonathen.authentication_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.yonathen.authentication_service.dto.LoginRequestDto;
import io.github.yonathen.authentication_service.dto.MessageResponseDto;
import io.github.yonathen.authentication_service.dto.SignUpRequestDto;
import io.github.yonathen.authentication_service.dto.JwtResponseDto;
import io.github.yonathen.authentication_service.jwt.JwtUtils;
import io.github.yonathen.authentication_service.model.ERole;
import io.github.yonathen.authentication_service.model.Role;
import io.github.yonathen.authentication_service.model.User;
import io.github.yonathen.authentication_service.repository.RoleRepository;
import io.github.yonathen.authentication_service.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  public JwtResponseDto login(LoginRequestDto requestDTO) {
    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        requestDTO.getUsername(), requestDTO.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = jwtUtils.generateJwtToken(auth);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return new JwtResponseDto(
      jwt, 
      "Bearer",
      userDetails.getId(), 
      userDetails.getUsername(), 
      userDetails.getEmail(), 
      roles
    );
  }

  public MessageResponseDto register(SignUpRequestDto signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new RuntimeException("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new RuntimeException("Error: Email is already in use!");
    }

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "moderator":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    User user = new User(
      null,
      signUpRequest.getUsername(),
      signUpRequest.getEmail(),
      passwordEncoder.encode(signUpRequest.getPassword()),
      roles
    );
    userRepository.save(user);
    return new MessageResponseDto("User registered successfully");
  }

}
