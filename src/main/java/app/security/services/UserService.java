package app.security.services;

import app.security.domain.User;
import app.security.domain.Workout;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //userConsumer
    User saveUser(User user);

//    userTeacher
    User getUserById(Long id);
    User getUser(String username);
    List<User> getUsers(int pageNumber);
    List<Workout> getAllWorkoutFromUser(String username);
    Workout getSpecificWorkoutFromUser(String username, Long workoutId);
    Void deleteWorkoutById(String username, Long workoutId);
    User createWorkout(String username, Workout workout);
    User updateWorkout(String username, Workout workout);


}
