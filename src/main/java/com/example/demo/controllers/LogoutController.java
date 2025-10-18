package com.example.demo.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class LogoutController {
/*
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
    }*/
}
