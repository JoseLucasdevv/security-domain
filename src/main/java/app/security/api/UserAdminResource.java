package app.security.api;

import app.security.Enum.TypeRole;
import app.security.dto.RegisterDTO;
import app.security.dto.UserDTO;
import app.security.exceptions.Exception;
import app.security.exceptions.HashError;
import app.security.services.AuthService;
import app.security.services.UserService;
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

    @GetMapping("/admin/user/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


    @GetMapping("/admin/user/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username ){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }


    @GetMapping("/admin/user/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/admin/user/delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }


}
