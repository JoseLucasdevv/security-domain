package app.security.types;

import app.security.Enum.MuscularGroup;
import app.security.Enum.WeekDay;
import java.time.LocalDateTime;


public record WorkoutDTO<T>(Long id, String name, String series, WeekDay weekday, MuscularGroup musculargroup, String description, String nameOfTeacher,
                            T user,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
}
