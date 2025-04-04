package app.security.services;
import app.security.Enum.TypeRole;
import app.security.MapperDTO.UserMapper;
import app.security.domain.User;
import app.security.dto.UserUpdateDTO;
import app.security.exceptions.Exception;
import app.security.repository.UserRepository;
import app.security.dto.UserDTO;
import app.security.services.validations.ResponseUserUpdateValidation;
import app.security.services.validations.UserUpdateValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserUpdateValidation userUpdateValidation;
    private final UserRepository userRepository;


    @Override
    public void saveUser(User user) {
        log.info("save new user {} in database",user.getName());
        this.userRepository.save(user);
    }

    @Override
    public UserDTO getUserConsumerById(Long id) {

        var user = this.userRepository.getUserById(TypeRole.USER,id);
        if(user == null) throw new Exception("Cannot find this user",HttpStatus.NOT_FOUND);
        log.info("get user {}",user.getUsername());

        return UserMapper.UserToDTO(user);

    }

    @Override
    public UserDTO getUserById(Long id) {
        var user = this.userRepository.findById(id).orElseThrow(()-> new Exception("Can't find this user",HttpStatus.NOT_FOUND));
        log.info("get user {}",user.getUsername());
        return UserMapper.UserToDTO(user);

    }


    @Override
    public List<UserDTO> getUsersConsumers(int pageNumber) {
        if(pageNumber < 1) pageNumber = 1;
        Pageable page = PageRequest.of(pageNumber - 1 ,10);
        log.info("get all UsersConsumers");

        List<User> users = this.userRepository.getAllByRoleNameUser(TypeRole.USER,page).stream().toList();

        return users.stream().map(UserMapper::UserToDTO).toList();
    }

    @Override
    public List<UserDTO> getAllUsers(int pageNumber) {
        if(pageNumber < 1) pageNumber = 1;
        Pageable page = PageRequest.of(pageNumber - 1 ,10);
        log.info("get all Users");

        List<User> users = this.userRepository.getAllUsers(page).stream().toList();

        return users.stream().map(UserMapper::UserToDTO).toList();
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User usr = this.userRepository.findByUsername(username);
        if(usr == null) throw new Exception("can't find this user", HttpStatus.NOT_FOUND);

        return UserMapper.UserToDTO(usr);

    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User usr = this.userRepository.findByEmail(email);
        if(usr == null) throw new Exception("can't find this user", HttpStatus.NOT_FOUND);
        return UserMapper.UserToDTO(usr);

    }

    @Override
    public UserDTO deleteUser(Long id) {
        User usr = this.userRepository.findById(id).orElseThrow(()-> new Exception("Can't find this user", HttpStatus.NOT_FOUND));
        this.userRepository.delete(usr);
        return UserMapper.UserToDTO(usr);
    }

    @Override
    public void deleteUserByUsername(String username) {
        this.userRepository.deleteUserByUsername(username);
    }

    @Override
    public ResponseUserUpdateValidationResource updateUser(Long id, UserUpdateDTO userUpdateDTO){

        User user = this.userRepository.findById(id).orElseThrow(()-> new Exception("this user doesn't exists", HttpStatus.NOT_FOUND));

        ResponseUserUpdateValidation userUpdated = this.userUpdateValidation.validationUpdate(user,userUpdateDTO);

        this.userRepository.save(userUpdated.user());

        return new ResponseUserUpdateValidationResource(UserMapper.UserToDTO(userUpdated.user()), userUpdated.resultValidation());


    }

    @Override
    public List<UserDTO> getAllUsersWithRole(TypeRole role, int pageNumber) {
        if(pageNumber < 1) pageNumber = 1;
        Pageable page = PageRequest.of(pageNumber - 1 ,10);
        return this.userRepository.getAllUsersWithRole(role,page).stream().map(UserMapper::UserToDTO).toList();
    }

    public record ResponseUserUpdateValidationResource(UserDTO user,Map<String,String> constraintValidations){}

}
