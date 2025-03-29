package app.security.api;


import app.security.dto.WorkoutDTO;
import app.security.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserConsumerResource {
    private final WorkoutService workoutService;


    @GetMapping("/user/resource/workouts")
    public ResponseEntity<List<WorkoutDTO<String>>> getMyWorkout(){
        try{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ResponseEntity.ok(workoutService.getAllWorkoutFromUser(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
