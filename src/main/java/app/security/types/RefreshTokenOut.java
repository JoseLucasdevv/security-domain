package app.security.types;

import java.time.Instant;

public record RefreshTokenOut(String newAccessToken, Instant newAccessTokenExpiresAt, String refreshToken,Instant refreshTokenExpiresAt,String TypeOfToken) {

}
