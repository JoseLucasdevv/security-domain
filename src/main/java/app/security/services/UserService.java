package app.security.services;

import app.security.domain.User;
import app.security.dto.UserDTO;
import app.security.dto.WorkoutCreateDTO;
import app.security.dto.WorkoutDTO;
import app.security.dto.WorkoutUpdateDTO;

import java.util.List;

public interface UserService {
    //userConsumer
    void saveUser(User user);

//    userTeacher
    UserDTO getUserById(Long id);
    List<UserDTO> getUsers(int pageNumber);
    List<WorkoutDTO<String>> getAllWorkoutFromUser(String username);
    WorkoutDTO<String> getSpecificWorkoutFromUser(String username , Long workoutId);
    Void deleteWorkoutById(Long userId, Long workoutId);
    UserDTO createWorkout(Long userId, WorkoutCreateDTO workout, String nameOfTeacher);
    UserDTO updateWorkout(Long workoutId, Long userId, WorkoutUpdateDTO workout, String nameOfTeacher);


}
