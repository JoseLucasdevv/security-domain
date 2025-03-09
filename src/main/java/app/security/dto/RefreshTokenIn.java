package app.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenIn(@NotBlank(message = "cannot be blank") @NotNull(message = "cannot be Null") String refreshToken) {
}
