package app.security.repository;

import app.security.domain.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken,Long> {
    Optional<EmailConfirmationToken> findEmailConfirmationTokenByToken(String token);
}
