package app.security.services;

import app.security.types.RefreshTokenIn;
import app.security.types.RefreshTokenOut;

import java.time.Instant;

public interface RefreshTokenService {

    String generateRefreshToken();
    Instant getExpiresAtRefreshToken();
    RefreshTokenOut verify(RefreshTokenIn refreshToken);
}
