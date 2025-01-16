package app.security.services;

import app.security.domain.Role;
import app.security.domain.User;

import java.util.List;

public interface UserService {

    User SaveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List<User> getUsers();

}
