package app.security.repository;

import app.security.Enum.TypeRole;
import app.security.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role.name = :role_name")
    Page<User> getAllByRoleNameUser(@Param("role_name") TypeRole roleName,Pageable page);
}
