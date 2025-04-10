package app.security.services.validations;

import app.security.domain.User;
import app.security.dto.UserDTOIn;
import app.security.dto.UserUpdateDTO;

import app.security.exceptions.Exception;
import app.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//should update ->name,username,email,password
@Service
@RequiredArgsConstructor
public class UserUpdateValidation {
public final HashMap<String, String> resultValidation = new HashMap<String, String>();
private final UserRepository userRepository;
private boolean validateUpdate = false;
    public ResponseUserUpdateValidation validationUpdate(User userAlreadyExist, UserDTOIn userUpdateValidation){
        resultValidation.clear();
        this.validateName(userAlreadyExist,userUpdateValidation.name());
        this.validateUsername(userAlreadyExist,userUpdateValidation.username());
        this.validateEmail(userAlreadyExist,userUpdateValidation.email());
        this.validatePassword(userAlreadyExist,userUpdateValidation.password());

        if(!this.validateUpdate) throw new Exception("Nothing to update", HttpStatus.BAD_REQUEST);

        userAlreadyExist.setUpdatedAt(LocalDateTime.now());
        resultValidation.put("updated",userAlreadyExist.getUpdatedAt().toString());
        return new ResponseUserUpdateValidation(userAlreadyExist,this.resultValidation);
    }

    private void validateName(User userAlreadyExist, String name){

        if(name != null && !name.isBlank() && name.length() >= 3 && name.length() <= 55){
            userAlreadyExist.setName(name.trim());
            resultValidation.put("fieldName","Update Success");
            this.validateUpdate = true;
            return;

        }
        resultValidation.put("fieldName","check the minimum requirements to update the FieldName");
    }

    private void validateUsername(User userAlreadyExist, String username) {

        if(username != null && !username.isBlank() && username.length() >= 3 && username.length() <= 55){
            User userFromDB = this.userRepository.findByUsername(username.trim());

            if(userFromDB != null) {
                resultValidation.put("fieldUsername","this username isn't allowed");
                return;
            }

            userAlreadyExist.setUsername(username.trim());
            resultValidation.put("fieldUsername","Update Success");
            this.validateUpdate = true;
            return;
        }
        resultValidation.put("fieldUsername","check the minimum requirements to update the FieldUsername");
    }

    private void validateEmail(User userAlreadyExist, String email){
        if(email != null && !email.isBlank()){
            User usrFromDB = this.userRepository.findByEmail(email);
            if(usrFromDB != null){
                this.resultValidation.put("fieldEmail","this email have been already used from another user.");
                return;
            }


            userAlreadyExist.setEmail(email);
            resultValidation.put("fieldEmail","Updated Success");
            this.validateUpdate = true;
            return;
        }

        resultValidation.put("fieldEmail","check the minimum requirements to update the FieldEmail");

    }

    private void validatePassword(User userAlreadyExist, String password){
        if(password != null && !password.isBlank()){
            String patternValidatePassword = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";
            Pattern pattern = Pattern.compile(patternValidatePassword);
            Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()){
            resultValidation.put("fieldPassword","check the minimum requirements to update the fieldPassword");
            return;
        }

        String newEncryptedPassword = new BCryptPasswordEncoder().encode(password);
        userAlreadyExist.setPassword(newEncryptedPassword);
        this.validateUpdate = true;
        resultValidation.put("fieldPassword","updated success");
        return;
        }
        resultValidation.put("fieldPassword","check the minimum requirements to update the fieldPassword");
    }

    }

