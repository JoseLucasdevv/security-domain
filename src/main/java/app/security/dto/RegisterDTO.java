package app.security.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(@NotNull(message = "cannot be null") @NotBlank(message = "cannot be blank") @Size(min= 3,max = 55,message = "name should have between 3 and 55 characters")String name,
                          @NotNull(message = "cannot be null") @NotBlank(message = "cannot be blank") @Size(min= 3,max = 55,message = "username should have between 3 and 55 characters")String username,
                          @NotNull(message = "cannot be null") @NotBlank(message = "cannot be blank")@Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$",message = "your password should have at least 8 characters One UpperCase letter and One Especial Character")
                          String password,
                          String confirmPassword)
{
}
