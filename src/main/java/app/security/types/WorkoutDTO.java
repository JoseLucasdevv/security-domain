package app.security.types;

import app.security.Enum.WeekDay;
import app.security.domain.User;

import java.util.Date;

public record WorkoutDTO(Long id, String name, String series, WeekDay weekday, String description, String nameOfTeacher,
                         User user,
                         Date createdAt, Date updatedAt) {
}
