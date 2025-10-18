package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.keycloak.KeycloakAdminService;
import com.example.demo.model.UserInput;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserInput userInput) {
        return ResponseEntity.ok(userService.createNewUser(userInput));
    }

    /*@GetMapping
    public ResponseEntity<List<UserRepresentation>> getUsers() {
  //      return ResponseEntity.ok(keycloakAdminService.getAllUsers());
    }*/
}
