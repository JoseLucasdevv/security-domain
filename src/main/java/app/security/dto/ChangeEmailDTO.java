package app.security.dto;

import jakarta.validation.constraints.Email;

public record ChangeEmailDTO(@Email String newEmail, String password){
}
