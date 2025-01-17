package app.security.repository;

import app.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {
    User findByUsername(String username);
}
