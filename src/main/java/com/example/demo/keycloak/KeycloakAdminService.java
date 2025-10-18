package com.example.demo.keycloak;

import com.example.demo.model.UserInput;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class KeycloakAdminService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private Keycloak keycloak;

    @PostConstruct
    private void init() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("spring-boot-app-realm")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .build();
    }

    public String createUser(UserInput user) {


        // 1️⃣ Create user representation
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setAttributes(Map.of("createdByApp", List.of("true")));

        // 2️⃣ Create the user
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }

        // 3️⃣ Extract the user ID from the Location header
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        response.close();

        // 4️⃣ Set password
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(user.getPassword());
        cred.setTemporary(false);

        List<RoleRepresentation> roleReps = List.of(keycloak.realm(realm).roles().get(user.getRole()).toRepresentation());
        keycloak.realm(realm).users().get(userId).resetPassword(cred);
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(roleReps);// realm level role -> admin, sper admin etc

        return userId;
    }

    public List<UserRepresentation> getAllUsers() {
        return keycloak.realm(realm).users().list();
    }
}
