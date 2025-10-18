package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {

    private final UserService userService;

    public CustomJwtAuthenticationConverter(UserService userService) {
        this.userService = userService;
        this.setJwtGrantedAuthoritiesConverter(this::convertJwtToAuthorities);
    }

    private Collection<GrantedAuthority> convertJwtToAuthorities(Jwt jwt) {
        // Extract username from JWT
        String username = jwt.getClaimAsString("preferred_username"); // or "sub"
        // Load user from database
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Map user roles to Spring authorities
        // roles like ROLE_ADMIN

        // Optional: set the user object in SecurityContext
        // This is automatically done by JwtAuthenticationToken if we pass it as principal
        return user.getRoles().stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new) // roles like ROLE_ADMIN
                .collect(Collectors.toSet());
    }
}

