package app.security.types;

import app.security.domain.Role;
import app.security.domain.Workout;

import java.util.Collection;

public record UserDTO(Long id, String name, String username, String password, Role role, Collection<Workout> workout) {

}
