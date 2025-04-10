package app.security.repository;

import app.security.domain.CodeVerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeVerifyEmailRepository extends JpaRepository<CodeVerifyEmail,Long> {

    Optional<CodeVerifyEmail> findCodeVerifyEmailByCode(String code);

}
