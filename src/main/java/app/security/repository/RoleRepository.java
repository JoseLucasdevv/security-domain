package app.security.repository;

import app.security.Enum.TypeRole;
import app.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByname(TypeRole name);
}
