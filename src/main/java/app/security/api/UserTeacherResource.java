package app.security.api;

import app.security.services.UserService;
import app.security.types.UserDTO;
import app.security.types.WorkoutDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTeacherResource {

    private final UserService userService;
    //Teacher Resource
    @GetMapping("/teacher/users/{pageNumber}")
    public ResponseEntity<List<UserDTO>> getUsers(@PathVariable int pageNumber){

        return ResponseEntity.ok().body(userService.getUsers(pageNumber));
    }

    @GetMapping("/teacher/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
            return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/teacher/workout/save/{userId}")
    public ResponseEntity<UserDTO> saveWorkout(@PathVariable Long userId,@RequestBody WorkoutDTO workout){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String extractUsername = authentication.getName();
        return ResponseEntity.status(201).body(this.userService.createWorkout(userId,workout,extractUsername));
    }

    @PutMapping("/teacher/workout/update/{userId}/{workoutId}")
    public ResponseEntity<UserDTO> updateWorkout(@PathVariable Long userId,@PathVariable Long workoutId,@RequestBody WorkoutDTO workout){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String extractUsername = authentication.getName();
        return ResponseEntity.status(201).body(this.userService.updateWorkout(workoutId,userId,workout,extractUsername));
    }
    @GetMapping("/teacher/workouts/{username}")
    public ResponseEntity<List<WorkoutDTO<String>>> getAllTrainingFromUser(@PathVariable String username){
        return ResponseEntity.ok(this.userService.getAllWorkoutFromUser(username));
    }

    @GetMapping("/teacher/workout/{username}/{workoutId}")
    public ResponseEntity<WorkoutDTO> getTrainingFromUser(@PathVariable String username,@PathVariable Long workoutId){
        return ResponseEntity.ok(this.userService.getSpecificWorkoutFromUser(username, workoutId));
    }

    @DeleteMapping("/teacher/workout/delete/{userId}/{workoutId}")
    public ResponseEntity deleteWorkout(@PathVariable Long userId,@PathVariable Long workoutId){
        this.userService.deleteWorkoutById(userId,workoutId);
        return ResponseEntity.ok().build();
    }
}
