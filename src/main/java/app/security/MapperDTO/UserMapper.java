package app.security.MapperDTO;

import app.security.domain.User;
import app.security.dto.UserDTO;
import app.security.dto.UserDTOIn;

public class UserMapper {


    public static UserDTO UserToDTO(User user){
        return new UserDTO(user.getUid(),user.getName(), user.getUsername(),user.getEmail(),user.getRole().getName(),user.getEmailConfirmed(),user.getWorkout().stream().toList());
    }

    public static User DtoToUser(UserDTOIn user){
    User userEntity = new User();
    userEntity.setName(user.name());
    userEntity.setUsername(user.username());
    userEntity.setEmail(user.email());
    userEntity.setPassword(user.password());

    return userEntity;



    }

}
