package app.security.services;

import app.security.Enum.TypeRole;
import app.security.domain.User;
import app.security.dto.UserDTO;
import app.security.dto.UserUpdateDTO;


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
    UserDTO deleteUser(Long id);
    UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);
    List<UserDTO> getAllUsersWithRole(TypeRole role, int pageNumber);
}
