package app.security.MapperDTO;

import app.security.domain.User;
import app.security.types.UserDTO;

public class UserMapper {


    public static UserDTO UserToDTO(User user){
        return new UserDTO(user.getId(),user.getName(), user.getUsername(), user.getPassword(),user.getRole(),user.getWorkout().stream().toList());
    }

    public static User DtoToUser(UserDTO userDTO){
        return new User(userDTO.id(),userDTO.name(),userDTO.username(),userDTO.password(),userDTO.role(),userDTO.workout());
    }

}
