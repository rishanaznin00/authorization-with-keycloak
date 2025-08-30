package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LogoutController {

    @GetMapping("/logout-success")
    @ResponseBody
    public String logoutPage() {
        return "You have been logged out.";
    }

    @PostMapping("/logout")
    public RedirectView keycloakLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.logout(); // logs out from Spring Security session

        String keycloakLogoutUrl = "http://localhost:8081/realms/spring-boot-app-realm/protocol/openid-connect/logout"
                + "?redirect_uri=http://localhost:8083/logout-success"; // adjust redirect

        return new RedirectView(keycloakLogoutUrl);
    }
}
