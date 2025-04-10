package app.security.dto;

public record UserUpdateDTO(String name,String username,String oldPassword,String newPassword ) {
}
