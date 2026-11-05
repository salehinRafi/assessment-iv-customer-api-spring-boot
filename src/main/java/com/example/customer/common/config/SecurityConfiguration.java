package com.example.customer.common.config;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${app.security.bearer-token}")
    private String bearerToken;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, OpaqueTokenIntrospector bearerTokenValidator) 
            throws Exception{
        return httpSecurity.csrf(csrf ->csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(resource -> resource
                        .opaqueToken(tokenConfigurer ->tokenConfigurer.introspector(bearerTokenValidator)))
                .build();
    }

    @Bean
    OpaqueTokenIntrospector bearerTokenValidator(){
        return token -> {
            if (!bearerToken.trim().equals(token.trim())) {
                throw new BadOpaqueTokenException("Invalid Token Bearer");
            }
            return new DefaultOAuth2AuthenticatedPrincipal(Map.of("sub", "client"), List.of());
        };
    }
}
