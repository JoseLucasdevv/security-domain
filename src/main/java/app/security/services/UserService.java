package app.security.services;

import app.security.domain.User;
import app.security.dto.UserDTO;
import app.security.dto.WorkoutCreateDTO;
import app.security.dto.WorkoutDTO;
import app.security.dto.WorkoutUpdateDTO;

import java.util.List;

public interface UserService {
    //userConsumer
    void saveUser(User user);

//    userTeacher
    UserDTO getUserConsumerById(Long id);
    UserDTO getUserById(Long id);
    List<UserDTO> getUsersConsumers(int pageNumber);
    List<UserDTO> getAllUsers(int pageNumber);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
}
