package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class RestSecurityConfig {



    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
      http
              .securityMatcher("/api/**")
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers("/api/**").authenticated()
              )
              .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .csrf(AbstractHttpConfigurer::disable)
              .formLogin(AbstractHttpConfigurer::disable)
              .cors(withDefaults());


      return http.build();
    }

}
