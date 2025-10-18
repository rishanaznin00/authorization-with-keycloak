package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.keycloak.KeycloakAdminService;
import com.example.demo.model.UserInput;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final KeycloakAdminService keycloakAdminService;

    private final UserRepository userRepository;

    public UserService(KeycloakAdminService keycloakAdminService, UserRepository userRepository) {
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }



    @Transactional
    public User createNewUser(UserInput userInput) {
        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setEmail(userInput.getEmail());
        user.setRoles(Set.of(userInput.getRole()));
        userRepository.save(user);
        keycloakAdminService.createUser(userInput);
        return user;
    }
}
