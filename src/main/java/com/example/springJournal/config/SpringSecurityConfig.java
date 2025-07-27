package com.example.springJournal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springJournal.service.UserDetailsServiceImp;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())       // JSON APIs in Postman, no CSRF tokens
      .authorizeHttpRequests(auth -> auth
          .requestMatchers(HttpMethod.POST, "/user").permitAll()
        .requestMatchers(HttpMethod.POST, "/admin/create-admin").permitAll()
          .requestMatchers("/journal/**", "/public/**").permitAll()
          .requestMatchers("/user/**").authenticated()
          .requestMatchers("/admin/**").hasRole("ADMIN")
          .anyRequest().authenticated()
      )

      // Enable HTTP Basic for Postman (and/or keep formLogin for browsers)
      .httpBasic(Customizer.withDefaults())
      .formLogin(Customizer.withDefaults());

    return http.build();
}
    // Required so Spring Security knows how to authenticate
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        auth
          .userDetailsService(userDetailsServiceImp)
          .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
