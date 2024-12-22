package com.example.catalogmanager.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String ADMIN_ROLE = "admin";

  private final JwtConverter jwtConverter;

  public SecurityConfig(JwtConverter jwtConverter) {
    this.jwtConverter = jwtConverter;
  }

  @Bean
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers(HttpMethod.POST, "/api/v1/categories", "/api/v1/products")
                    .hasRole(ADMIN_ROLE)
                    .requestMatchers(HttpMethod.PUT, "/api/v1/categories", "/api/v1/products")
                    .hasRole(ADMIN_ROLE)
                    .requestMatchers(
                        HttpMethod.DELETE, "/api/v1/categories/{id}", "/api/v1/products/{id}")
                    .hasRole(ADMIN_ROLE)
                    .anyRequest()
                    .authenticated())
        .httpBasic(withDefaults());
    http.oauth2ResourceServer(
        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));
    return http.build();
  }
}
