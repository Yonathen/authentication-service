package io.github.yonathen.authentication_service.service;

import io.github.yonathen.authentication_service.dto.JwtResponseDto;
import io.github.yonathen.authentication_service.dto.LoginRequestDto;
import io.github.yonathen.authentication_service.dto.MessageResponseDto;
import io.github.yonathen.authentication_service.dto.SignUpRequestDto;

public interface AuthService {
  JwtResponseDto login(LoginRequestDto requestDTO);
  
  MessageResponseDto register(SignUpRequestDto signUpRequest);
}
