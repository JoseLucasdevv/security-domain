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

    @GetMapping("/teacher/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
       try{
           return ResponseEntity.ok().body(userService.getUserConsumerById(id));
       }catch(Exception e){
                HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
           return ResponseEntity.badRequest().body(objectHashError);
       }

    }

    @PostMapping("/teacher/workout/{userId}")
    public ResponseEntity<?> saveWorkout(@PathVariable Long userId,@RequestBody @Valid WorkoutCreateDTO workout){
        try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String extractUsername = authentication.getName();
        return ResponseEntity.status(201).body(this.workoutService.createWorkout(userId,workout,extractUsername));
        }catch(Exception e){
            HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(objectHashError);

        }
    }

    @PutMapping("/teacher/workout/{userId}/{workoutId}")
    public ResponseEntity<?> updateWorkout(@PathVariable Long userId,@PathVariable Long workoutId,@RequestBody WorkoutUpdateDTO workout){
        try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String extractUsername = authentication.getName();
        return ResponseEntity.status(201).body(this.workoutService.updateWorkout(workoutId,userId,workout,extractUsername));
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
    public ResponseEntity<?> deleteWorkout(@PathVariable Long userId,@PathVariable Long workoutId){
        try {
            this.workoutService.deleteWorkoutById(userId, workoutId);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(objectHashError);
        }
    }
}
