package app.security.api;

import app.security.Enum.TypeRole;
import app.security.services.AuthService;
import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import app.security.exceptions.Exception;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class AuthResource {
@Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity registerResource(@RequestBody RegisterDTO form){
try{
        authService.register(form, TypeRole.USER);
        return ResponseEntity.ok().build();
}catch(Exception e){
    return ResponseEntity.badRequest().body(e.getMessage());
}
    }



    @PostMapping("/auth")
    public ResponseEntity authResource(@RequestBody AuthDTO form){
        try{
            var token = authService.auth(form);
            return ResponseEntity.ok(token);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e);

        }


    }


}
