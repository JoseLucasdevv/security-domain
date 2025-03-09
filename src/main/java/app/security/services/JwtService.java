package app.security.services;

import app.security.infra.security.UserAuthenticated;

import java.time.Instant;

public interface JwtService {
    String generateToken(UserAuthenticated user);
    String validateToken(String token);
    Instant extractExpiresAt(String token);
}
