package app.security.api;

import app.security.domain.User;
import app.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTeacherResource {
    private final UserService userService;

    //Teacher Resource
    @GetMapping("/teacher/users/{pageNumber}")
    public ResponseEntity<List<User>> getUsers(@PathVariable int pageNumber){

        return ResponseEntity.ok().body(userService.getUsers(pageNumber));
    }

    @GetMapping("/teacher/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
            return ResponseEntity.ok().body(userService.getUser(username));
    }

}
