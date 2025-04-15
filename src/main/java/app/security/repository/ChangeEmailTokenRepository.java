package app.security.repository;

import app.security.domain.ChangeEmailToken;
import app.security.domain.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangeEmailTokenRepository extends JpaRepository<ChangeEmailToken,Long> {

    Optional<ChangeEmailToken> findByToken(String token);
}
