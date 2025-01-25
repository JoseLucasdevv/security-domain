package app.security.services;

import app.security.Enum.TypeRole;
import app.security.domain.Role;
import app.security.domain.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, TypeRole roleName);
    User getUser(String username);
    List<User> getUsers();

}
