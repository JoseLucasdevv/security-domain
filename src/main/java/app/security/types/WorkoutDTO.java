package app.security.types;

import app.security.Enum.WeekDay;
import app.security.domain.User;


import java.time.LocalDateTime;


public record WorkoutDTO(Long id, String name, String series, WeekDay weekday, String description, String nameOfTeacher,
                         User user,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
}
