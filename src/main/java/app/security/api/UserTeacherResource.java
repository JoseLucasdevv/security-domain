package app.security.api;


import app.security.exceptions.Exception;
import app.security.exceptions.HashError;
import app.security.services.UserService;
import app.security.dto.UserDTO;
import app.security.dto.WorkoutCreateDTO;
import app.security.dto.WorkoutDTO;
import app.security.dto.WorkoutUpdateDTO;
import app.security.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTeacherResource {
    private final WorkoutService workoutService;
    private final UserService userService;
    //Teacher Resource
    @GetMapping("/teacher/users/{pageNumber}")
    public ResponseEntity<List<UserDTO>> getUsers(@PathVariable int pageNumber){
        try{
        return ResponseEntity.ok().body(userService.getUsersConsumers(pageNumber));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/teacher/user/{uid}")
    public ResponseEntity<?> getUser(@PathVariable String uid){
       try{
           return ResponseEntity.ok().body(userService.getUserConsumerByUid(UUID.fromString(uid)));
       }catch(Exception e){
                HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
           return ResponseEntity.badRequest().body(objectHashError);
       }

    }

    @PostMapping("/teacher/workout/{userUid}")
    public ResponseEntity<?> saveWorkout(@PathVariable String userUid,@RequestBody @Valid WorkoutCreateDTO workout){
        try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String extractUsername = authentication.getName();
        return ResponseEntity.status(201).body(this.workoutService.createWorkout(UUID.fromString(userUid),workout,extractUsername));
        }catch(Exception e){
            HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(objectHashError);

        }
    }

    @PutMapping("/teacher/workout/{userUid}/{workoutId}")
    public ResponseEntity<?> updateWorkout(@PathVariable String userUid,@PathVariable Long workoutId,@RequestBody WorkoutUpdateDTO workout){
        try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String extractUsername = authentication.getName();
        return ResponseEntity.status(201).body(this.workoutService.updateWorkout(workoutId,UUID.fromString(userUid),workout,extractUsername));
        }catch(Exception e){
            HashMap<String,String> errors = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(errors);

        }

    }
    @GetMapping("/teacher/workouts/{username}")
    public ResponseEntity<List<WorkoutDTO<String>>> getAllTrainingFromUser(@PathVariable String username){
        try{
        return ResponseEntity.ok(this.workoutService.getAllWorkoutFromUser(username));
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/teacher/workout/{username}/{workoutId}")
    public ResponseEntity<?> getTrainingFromUser(@PathVariable String username,@PathVariable Long workoutId){
        try{
        return ResponseEntity.ok(this.workoutService.getSpecificWorkoutFromUser(username, workoutId));
        }catch(Exception e){
            HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(objectHashError);
        }

    }

    @DeleteMapping("/teacher/workout/{userId}/{workoutId}")
    public ResponseEntity<?> deleteWorkout(@PathVariable UUID userId,@PathVariable Long workoutId){
        try {
            this.workoutService.deleteWorkoutById(userId, workoutId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(objectHashError);
        }
    }
}
