package app.security.repository;

import app.security.domain.CodeVerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeVerifyEmailRepository extends JpaRepository<CodeVerifyEmail,Long> {
}
