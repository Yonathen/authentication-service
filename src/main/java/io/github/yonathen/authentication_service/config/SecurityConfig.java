package io.github.yonathen.authentication_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.yonathen.authentication_service.jwt.JwtAuthEntryPoint;
import io.github.yonathen.authentication_service.jwt.JwtAuthFilter;
import io.github.yonathen.authentication_service.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  private JwtAuthEntryPoint jwtAuthEntryPoint;

  @Bean
  public JwtAuthFilter jwtAuthFilter() {
    return new JwtAuthFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsServiceImpl);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
      .authorizeHttpRequests((authz) -> authz
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/api/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
      )
      .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
      .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
    http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
