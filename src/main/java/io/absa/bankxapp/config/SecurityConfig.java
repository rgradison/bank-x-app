package io.absa.bankxapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http.csrf(csrf -> csrf
           .ignoringRequestMatchers("/public/**", "/authenticate/**")
       )
           .authorizeHttpRequests(auth -> auth
               .requestMatchers("/api/customers/**", "/api/accounts/**").permitAll()
               .anyRequest().authenticated()
           )
           .httpBasic(withDefaults());

       return http.build();
    }
}