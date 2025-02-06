package app.security.services;

import app.security.infra.security.UserAuthenticated;

public interface JwtService {
    String generateToken(UserAuthenticated user);
    String validateToken(String token);
}
