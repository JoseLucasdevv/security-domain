package app.security.api;

import app.security.Enum.TypeRole;
import app.security.dto.RegisterDTO;
import app.security.dto.UserDTO;
import app.security.dto.UserUpdateDTO;
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

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("/admin/user/{id}")
    public ResponseEntity<UserServiceImpl.ResponseUserUpdateValidationResource> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdate) {

        return ResponseEntity.ok(userService.updateUser(id,userUpdate));
    }

    @GetMapping("/admin/users/{role}/{pageNumber}")
    public ResponseEntity<List<UserDTO>> getAllUsersWithRole(@PathVariable TypeRole role ,@PathVariable int pageNumber){
        return ResponseEntity.ok(userService.getAllUsersWithRole(role,pageNumber));
    }

}
