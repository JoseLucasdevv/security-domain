package app.security.domain;

import app.security.Enum.WeekDay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String series;
    private WeekDay weekday;
    private String description;
    private String nameOfTeacher;
    private Date createdAt;
    private Date updatedAt;
}
