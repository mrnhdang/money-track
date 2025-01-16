package com.huda.money_track.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutService implements LogoutHandler {
    @Autowired
    private JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Retrieve the Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;

        // Check if the header starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        token = authHeader.substring(7); // Extract token
        String newTokn = jwtService.logoutToken(token);
        // TODO: 
        //  For now, skip this
        //  request to create a table (Token) to contain the jwt and mark it isLoggedOut
    }
}
