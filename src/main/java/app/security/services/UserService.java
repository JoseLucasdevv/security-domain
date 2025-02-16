package app.security.services;

import app.security.domain.User;
import app.security.domain.Workout;
import app.security.types.UserDTO;
import app.security.types.WorkoutDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //userConsumer
    void saveUser(User user);

//    userTeacher
    UserDTO getUserById(Long id);
    List<UserDTO> getUsers(int pageNumber);
    List<WorkoutDTO<String>> getAllWorkoutFromUser(String username);
    WorkoutDTO<String> getSpecificWorkoutFromUser(String username , Long workoutId);
    Void deleteWorkoutById(Long userId, Long workoutId);
    UserDTO createWorkout(Long userId, WorkoutDTO<?> workout);
    UserDTO updateWorkout(Long workoutId, Long userId,WorkoutDTO<?> workout);


}
