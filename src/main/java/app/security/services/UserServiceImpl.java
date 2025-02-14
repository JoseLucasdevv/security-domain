package app.security.services;

import app.security.Enum.TypeRole;
import app.security.domain.User;
import app.security.domain.Workout;
import app.security.repository.UserRepository;
import app.security.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;


    @Override
    public User saveUser(User user) {
        log.info("save new user {} in database",user.getName());
        return this.userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        var user = this.userRepository.getUserById(TypeRole.USER,id);
        log.info("get user {}",user.getUsername());
        return user;
    }


    @Override
    public User getUser(String username) {
        log.info("get user {}",username );
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber -1 ,10);
        log.info("get all Users");

        return this.userRepository.getAllByRoleNameUser(TypeRole.USER,page).stream().collect(Collectors.toList());
    }

    @Override
    public List<Workout> getAllWorkoutFromUser(String username) {
        log.info("get all workout from user");
        return this.userRepository.getAllWorkoutFromUser(TypeRole.USER , username);
    }

    @Override
    public Workout getSpecificWorkoutFromUser(String username, Long workoutId) {
        List<Workout> listWorkout = this.userRepository.getAllWorkoutFromUser(TypeRole.USER,username);

        return listWorkout.stream().filter(w -> workoutId.equals(w.getId())).findFirst().orElse(null);
        // return this.workoutRepository.findById(workoutId).orElseThrow(() ->  new EntityNotFoundException("Workout not found"));

    }

    @Override
    public Void deleteWorkoutById(String username, Long workoutId) {
        return null;
    }

    @Override
    public User createWorkout(String username, Workout workout) {
        return null;
    }

    @Override
    public User updateWorkout(String username, Workout workout) {
        return null;
    }
}
