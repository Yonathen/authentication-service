package io.github.yonathen.authentication_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.yonathen.authentication_service.dto.JwtResponseDto;
import io.github.yonathen.authentication_service.dto.LoginRequestDto;
import io.github.yonathen.authentication_service.dto.MessageResponseDto;
import io.github.yonathen.authentication_service.dto.SignUpRequestDto;
import io.github.yonathen.authentication_service.service.AuthServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  
  @Autowired
  private AuthServiceImpl authService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto requestDTO) {
    return ResponseEntity.ok(authService.login(requestDTO)); 
  }

  @PostMapping("/register")
  public ResponseEntity<MessageResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequest) {
    logger.info("Registering user: {}", signUpRequest.getUsername() + "-" + signUpRequest.getPassword());
    return ResponseEntity.ok(authService.register(signUpRequest));  
  }
}
