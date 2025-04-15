package app.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.security.domain.SetNewEmailToken;

import java.util.Optional;


public interface SetNewEmailTokenRepository extends JpaRepository<SetNewEmailToken,Long> {
    Optional<SetNewEmailToken> findByToken(String token);

}
