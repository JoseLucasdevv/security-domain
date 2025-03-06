package app.security.types;

import app.security.Enum.MuscularGroup;
import app.security.Enum.WeekDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutCreateDTO(@NotNull(message = "cannot be null")
                               @NotBlank(message = "cannot be blank") String name,
                               @NotNull(message = "cannot be null")
                               @NotBlank(message = "cannot be blank") String series,
                               String description,
                               WeekDay weekday,
                               MuscularGroup muscularGroup) {
}
