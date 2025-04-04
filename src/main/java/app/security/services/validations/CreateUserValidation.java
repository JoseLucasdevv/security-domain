package app.security.services.validations;


import app.security.domain.User;
import app.security.exceptions.Exception;
import app.security.repository.UserRepository;
import app.security.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserValidation {
    private final UserRepository userRepository;

public void RegisterValidation(RegisterDTO form){
    User userByUsername = this.userRepository.findByUsername(form.username());
    User userByEmail = this.userRepository.findByEmail(form.email());

    if(userByUsername != null || userByEmail != null) throw new Exception("User already exists", HttpStatus.FOUND);

    if(!form.password().equals(form.confirmPassword())) throw new Exception("confirm password is incorrect",HttpStatus.CONFLICT);

}


}
