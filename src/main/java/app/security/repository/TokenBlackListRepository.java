package app.security.repository;

import app.security.domain.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList,Long> {
    Optional<TokenBlackList> findByTokenBlackList(String token);
}
