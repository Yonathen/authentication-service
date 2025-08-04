package io.github.yonathen.authentication_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
	@NotBlank
  private String username;

	@NotBlank
	private String password;
}

