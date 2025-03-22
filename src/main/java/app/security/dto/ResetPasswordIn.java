package app.security.dto;


import jakarta.validation.constraints.Pattern;

public record ResetPasswordIn(
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$"
                ,message = "your password should have at least 8 characters One UpperCase letter and One Especial Character")
        String newPassword) {
}
