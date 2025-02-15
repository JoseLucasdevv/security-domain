package app.security.api;

import app.security.domain.User;
import app.security.services.UserService;
import app.security.types.WorkoutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/teacher/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
            return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/teacher/workout/save/{userId}")
    public ResponseEntity<User> saveWorkout(@PathVariable Long userId,@RequestBody WorkoutDTO workout){
        return ResponseEntity.status(201).body(this.userService.createWorkout(userId,workout));
    }

    @PutMapping("/teacher/workout/update/{userId}/{workoutId}")
    public ResponseEntity<User> updateWorkout(@PathVariable Long userId,@PathVariable Long workoutId,@RequestBody WorkoutDTO workout){
        return ResponseEntity.status(201).body(this.userService.updateWorkout(workoutId,userId,workout));
    }
    @GetMapping("/teacher/workouts/{username}")
    public ResponseEntity<List<WorkoutDTO>> getAllTrainingFromUser(@PathVariable String username){
        return ResponseEntity.ok(this.userService.getAllWorkoutFromUser(username));
    }

}
