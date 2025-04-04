package app.security.repository;

import app.security.Enum.TypeRole;
import app.security.domain.User;
import app.security.domain.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.role.name = :role_name")
    Page<User> getAllByRoleNameUser(@Param("role_name") TypeRole roleName,Pageable page);

    @Query("SELECT u FROM User u WHERE u.role.name = :role_name AND u.id = :user_id")
    User getUserById(@Param("role_name") TypeRole roleName ,@Param ("user_id") long id);

    @Query("SELECT u.workout FROM User u WHERE u.role.name = :role_name AND u.username = :username")
    List<Workout> getAllWorkoutFromUser(@Param("role_name")TypeRole roleName,String username);

    @Query("SELECT u FROM User u")
    Page<User> getAllUsers(Pageable page);

    @Query("SELECT u FROM User u WHERE u.role.name = :role_name")
    Page<User> getAllUsersWithRole(@Param("role_name")TypeRole roleName, Pageable page);

    void deleteUserByUsername(String username);
}
