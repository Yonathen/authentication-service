package io.github.yonathen.authentication_service.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDto {
  private String token;
  private String type = "Bearer";
  private UUID id;
  private String username;
  private String email;
  private List<String> roles;
}

