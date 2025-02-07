package app.security.api;
import app.security.repository.RoleRepository;
import app.security.services.AuthService;

import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class AuthResource {
@Autowired
    AuthService authService;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity registerResource(@RequestBody RegisterDTO form){

        authService.register(form);


        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity authResource(@RequestBody AuthDTO form){
        try{
            var token = authService.auth(form);
            return ResponseEntity.ok(token);
        }catch(Exception e ){
            return ResponseEntity.badRequest().body(e);

        }


    }


}
