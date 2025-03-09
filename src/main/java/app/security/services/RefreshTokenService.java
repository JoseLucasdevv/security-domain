package app.security.services;

import app.security.dto.RefreshTokenIn;
import app.security.dto.RefreshTokenOut;

import java.time.Instant;

public interface RefreshTokenService {

    String generateRefreshToken();
    Instant getExpiresAtRefreshToken();
    RefreshTokenOut verify(RefreshTokenIn refreshToken);
}
