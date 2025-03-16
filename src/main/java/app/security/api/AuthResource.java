package app.security.api;

import app.security.Enum.TypeRole;
import app.security.dto.LogOutIn;
import app.security.exceptions.HashError;
import app.security.services.AuthService;
import app.security.dto.AuthDTO;
import app.security.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import app.security.exceptions.Exception;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/api")
public class AuthResource {
@Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<HashMap<String,String>> registerResource(@RequestBody @Valid RegisterDTO form){
try{
        authService.register(form, TypeRole.USER);
        HashMap<String,String> success = new HashMap<>();
        success.put("message","we almost there please confirm your Email: " +  form.email());
        return ResponseEntity.ok().body(success);
}catch(Exception e){

    HashMap<String,String> errors = HashError.createHashErrorOutput(e.getMessage());
    return ResponseEntity.badRequest().body(errors);
}
    }



    @PostMapping("/auth")
    public ResponseEntity<?> authResource(@RequestBody AuthDTO form){
        try{
            var token = authService.auth(form);
            return ResponseEntity.ok(token);
        }catch(Exception e){
            HashMap<String,String> errors = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(errors);

        }


    }

    @PostMapping("/logOut")
    public ResponseEntity<?> logOutResource(@RequestBody @Valid LogOutIn tokens){
        try{
            authService.logOut(tokens);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            HashMap<String,String> errors = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(errors);
        }


    }


}
