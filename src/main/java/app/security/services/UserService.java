package app.security.services;

import app.security.domain.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    User getUser(String username);
    List<User> getUsers();

}
