package app.security.dto;

import app.security.domain.Workout;

import java.util.List;

public record UserDTO(Long id, String name, String username, app.security.Enum.TypeRole role, List<Workout> workout) {}


