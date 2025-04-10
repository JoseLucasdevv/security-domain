    package app.security.api;

import app.security.Enum.TypeRole;
import app.security.dto.RegisterDTO;
import app.security.dto.UserDTO;
import app.security.dto.UserDTOIn;
import app.security.exceptions.Exception;
import app.security.exceptions.HashError;
import app.security.services.AuthService;
import app.security.services.UserService;
import app.security.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class UserAdminResource {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/admin/register-teacher")
    public ResponseEntity<HashMap<String,String>> register(@RequestBody @Valid RegisterDTO form){
        try{
            authService.register(form, TypeRole.TEACHER);
            HashMap<String,String> success = new HashMap<>();
            success.put("message","we almost there please confirm your Email: " +  form.email());
            return ResponseEntity.ok().body(success);
        }catch(Exception e){
            HashMap<String,String> errors = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(errors);
        }
    }
    @GetMapping("/admin/users/{pageNumber}")
    public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable int pageNumber){
        return ResponseEntity.ok(userService.getAllUsers(pageNumber));
    }

    @GetMapping("/admin/user")
    public ResponseEntity<UserDTO> getUserByParam(@RequestParam(required = false) String email,@RequestParam(required = false) String username){

        //pattern restfull Api
        UserDTO user;
        if(email != null) user = userService.getUserByEmail(email);
        else if(username != null) user = userService.getUserByUsername(username);
        else return ResponseEntity.badRequest().build();

        return user != null  ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();

    }

    @GetMapping("/admin/user/{uid}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String uid){
        return ResponseEntity.ok(userService.getUserByUid(UUID.fromString(uid)));
    }

    @DeleteMapping("/admin/user/{uid}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String uid){
        return ResponseEntity.ok(userService.deleteUser(UUID.fromString(uid)));
    }

    @PutMapping("/admin/user/{uid}")
        public ResponseEntity<UserServiceImpl.ResponseUserUpdateValidationResource> updateUser(@PathVariable String uid, @RequestBody UserDTOIn userUpdate) {

        return ResponseEntity.ok(userService.updateUser(UUID.fromString(uid),userUpdate));
    }

    @GetMapping("/admin/users/{role}/{pageNumber}")
    public ResponseEntity<List<UserDTO>> getAllUsersWithRole(@PathVariable TypeRole role ,@PathVariable int pageNumber){
        return ResponseEntity.ok(userService.getAllUsersWithRole(role,pageNumber));
    }

}
