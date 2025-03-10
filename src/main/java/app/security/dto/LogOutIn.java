package app.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LogOutIn(@NotNull(message = "can't be null") @NotBlank(message = "can't be blank") String accessToken, @NotNull(message = "can't be null") @NotBlank(message = "can't be blank")String refreshToken) {
}
