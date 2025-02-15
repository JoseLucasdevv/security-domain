package app.security.services;

import app.security.domain.User;
import app.security.domain.Workout;
import app.security.types.WorkoutDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //userConsumer
    User saveUser(User user);

//    userTeacher
    User getUserById(Long id);
    User getUser(String username);
    List<User> getUsers(int pageNumber);
    List<WorkoutDTO> getAllWorkoutFromUser(String username);
    Workout getSpecificWorkoutFromUser(String username , Long workoutId);
    Void deleteWorkoutById(Long userId, Long workoutId);
    User createWorkout(Long userId, WorkoutDTO workout);
    User updateWorkout(Long workoutId, Long userId,WorkoutDTO workout);


}
