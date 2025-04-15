package app.security.domain;

import app.security.Enum.MuscularGroup;
import app.security.Enum.WeekDay;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String series;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MuscularGroup muscularGroup;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeekDay weekday;
    private String description;
    @Column(nullable = false)
    private String nameOfTeacher;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}

