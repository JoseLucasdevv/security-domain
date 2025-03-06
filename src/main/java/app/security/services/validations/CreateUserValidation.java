package app.security.services.validations;


import app.security.exceptions.Exception;
import app.security.repository.UserRepository;
import app.security.types.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserValidation {
    private final UserRepository userRepository;

public void RegisterValidation(RegisterDTO form){
    var user = this.userRepository.findByUsername(form.username());
    if(user != null) throw new Exception("User already exists");

    if(!form.password().equals(form.confirmPassword())) throw new Exception("confirm password is incorrect");



}


}
