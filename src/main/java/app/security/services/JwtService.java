package app.security.services;

import app.security.infra.security.UserAuthenticated;
import com.auth0.jwt.interfaces.Claim;

import java.time.Instant;
import java.util.Map;

public interface JwtService {
    String generateToken(UserAuthenticated user);
    String validateToken(String token);
    Instant extractExpiresAt(String token);
    Map<String, Claim> extractClaims(String token);
}
