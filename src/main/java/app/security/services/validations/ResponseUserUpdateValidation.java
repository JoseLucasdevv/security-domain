package app.security.services.validations;

import app.security.domain.User;

import java.util.HashMap;

public record ResponseUserUpdateValidation(User user , HashMap<String, String> resultValidation) {
}
