package app.security.services;

import app.security.dto.UserDTO;
import app.security.dto.WorkoutCreateDTO;
import app.security.dto.WorkoutDTO;
import app.security.dto.WorkoutUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {

    List<WorkoutDTO<String>> getAllWorkoutFromUser(String username);
    WorkoutDTO<String> getSpecificWorkoutFromUser(String username , Long workoutId);
    void deleteWorkoutById(UUID userUid, Long workoutId);
    UserDTO createWorkout(UUID userUid, WorkoutCreateDTO workout, String nameOfTeacher);
    UserDTO updateWorkout(Long workoutId, UUID userUid, WorkoutUpdateDTO workout, String nameOfTeacher);


}
