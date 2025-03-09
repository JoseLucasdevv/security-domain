package app.security.api;

import app.security.services.UserService;
import app.security.dto.WorkoutDTO;
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
    private final UserService userService;

    @GetMapping("/user/resource/workouts")
    public ResponseEntity<List<WorkoutDTO<String>>> getMyWorkout(){
        try{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ResponseEntity.ok(userService.getAllWorkoutFromUser(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
