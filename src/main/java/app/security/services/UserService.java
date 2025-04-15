package app.security.services;

import app.security.Enum.TypeRole;
import app.security.domain.User;
import app.security.dto.UserDTO;
import app.security.dto.UserDTOIn;
import app.security.dto.UserUpdateDTO;


import java.util.List;
import java.util.UUID;

public interface UserService {
    //userConsumer
    void saveUser(User user);

//    userTeacher
    UserDTO getUserConsumerByUid(UUID uid);
    UserDTO getUserByUid(UUID uid);
    List<UserDTO> getUsersConsumers(int pageNumber);
    List<UserDTO> getAllUsers(int pageNumber);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    UserDTO deleteUser(UUID uid);
    void deleteUserByUsername(String username);
    void changeEmail(String token);
    UserServiceImpl.ResponseUserUpdateValidationResource updateUserByUsername(String username, UserUpdateDTO userUpdateDTO);
    UserServiceImpl.ResponseUserUpdateValidationResource updateUser(UUID uid, UserDTOIn userUpdateDTO);
    List<UserDTO> getAllUsersWithRole(TypeRole role, int pageNumber);
}
