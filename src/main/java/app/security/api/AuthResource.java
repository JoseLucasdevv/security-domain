package app.security.api;

import app.security.Enum.TypeRole;
import app.security.domain.Role;
import app.security.domain.User;
import app.security.infra.security.UserAuthenticated;
import app.security.repository.RoleRepository;
import app.security.services.JwtService;
import app.security.services.UserService;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AuthResource {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity registerResource(@RequestBody UserDTO form){


        String encryptedPassword = new BCryptPasswordEncoder().encode(form.password);
        User persistUser = new User();
        persistUser.setName(form.getName());
        persistUser.setPassword(encryptedPassword);
        persistUser.setUsername(form.getUsername());
        Role roleUser = new Role();
        roleUser.setName(TypeRole.USER);
        roleRepository.save(roleUser);
        persistUser.setRoles(List.of(roleUser));
        userService.saveUser(persistUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity authResource(@RequestBody Auth form){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = jwtService.generateToken((UserAuthenticated) auth.getPrincipal());
            return ResponseEntity.ok(token);
        }catch(Exception e ){
            return ResponseEntity.badRequest().body(e);

        }


    }


        @Data
        static class UserDTO {
            private String name;
            private String username;
            private String password;
        }

    @Data
    static class Auth {
        private String username;
        private String password;
    }
}
