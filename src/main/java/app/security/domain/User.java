package app.security.domain;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true, updatable = false)
    private UUID uid;
    private String name;
    private String username;
    private String email;
    private Boolean emailConfirmed = false;
    private String password;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER ,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TokenBlackList> accessTokenBlackList = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Workout> workout = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RefreshToken> refreshTokens = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<EmailConfirmationToken> emailConfirmationTokens = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ForgotPasswordToken> forgotPasswordTokens = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<CodeVerifyEmail> codeVerifyEmail = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ChangeEmailToken> changeEmailToken = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<SetNewEmailToken> newEmailToken = new ArrayList<>();


    @PrePersist
    private void prePersistUid(){
        UUID uuidGenerated = UUID.randomUUID();
        this.setUid(uuidGenerated);
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }

    public String getUid(){
        return this.uid.toString();
    }
}
