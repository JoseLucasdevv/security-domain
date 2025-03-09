package app.security.dto;


import java.time.Instant;

public record ResponseAuthentication(String accessToken, Instant accessTokenExpiresAt, String refreshToken, Long refreshTokenId,
                                     Instant refreshTokenExpiresAt, UserDTO user,String typeOfToken) {
}
