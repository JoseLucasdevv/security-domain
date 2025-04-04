package app.security.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
public class CodeVerifyEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
