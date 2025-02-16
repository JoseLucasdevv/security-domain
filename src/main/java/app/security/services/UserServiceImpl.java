package app.security.services;

import app.security.Enum.TypeRole;
import app.security.MapperDTO.UserMapper;
import app.security.domain.User;
import app.security.domain.Workout;
import app.security.repository.UserRepository;
import app.security.repository.WorkoutRepository;
import app.security.types.UserDTO;
import app.security.types.WorkoutDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;


    @Override
    public void saveUser(User user) {
        log.info("save new user {} in database",user.getName());
        this.userRepository.save(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        var user = this.userRepository.getUserById(TypeRole.USER,id);
        log.info("get user {}",user.getUsername());
        return UserMapper.UserToDTO(user);

    }

    @Override
    public List<UserDTO> getUsers(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber -1 ,10);
        log.info("get all Users");

        List<User> users = this.userRepository.getAllByRoleNameUser(TypeRole.USER,page).stream().collect(Collectors.toList());

        return users.stream().map(UserMapper::UserToDTO).toList();
    }

    @Override
    public List<WorkoutDTO<String>> getAllWorkoutFromUser(String username) {
        log.info("get all workout from user");

        List<WorkoutDTO<String>> listWorkoutDTO = new ArrayList<>();
         List<Workout> listWorkoutDomain = this.userRepository.getAllWorkoutFromUser(TypeRole.USER , username);

         listWorkoutDomain.stream().forEach(w ->{
             WorkoutDTO<String> workoutDTO = new WorkoutDTO<String>(w.getId(),w.getName(),w.getSeries(),w.getWeekday(),w.getDescription(),w.getNameOfTeacher(),w.getUser().getUsername(),w.getCreatedAt(),w.getUpdatedAt());
             listWorkoutDTO.add(workoutDTO);
         });

        return listWorkoutDTO;
    }

    @Override
    public WorkoutDTO<String> getSpecificWorkoutFromUser(String username, Long workoutId) {
        List<Workout> listWorkout = this.userRepository.getAllWorkoutFromUser(TypeRole.USER,username);
        Workout workout =  listWorkout.stream().filter(w -> workoutId.equals(w.getId())).findFirst().orElse(null);

        assert workout != null;
        return new WorkoutDTO<String>(workout.getId(),workout.getName(),workout.getSeries(),workout.getWeekday(),workout.getDescription(),workout.getNameOfTeacher(),workout.getUser().getUsername(),workout.getCreatedAt(),workout.getUpdatedAt());
        // return this.workoutRepository.findById(workoutId).orElseThrow(() ->  new EntityNotFoundException("Workout not found"));

    }

    @Override
    public Void deleteWorkoutById(Long userId, Long workoutId) {
        return null;
    }

    @Override
    public UserDTO createWorkout(Long userId, WorkoutDTO<?> workout) {
        User user = this.userRepository.getUserById(TypeRole.USER,userId);
        Workout workoutEntity = new Workout();
        workoutEntity.setName(workout.name());
        workoutEntity.setSeries(workout.series());
        workoutEntity.setNameOfTeacher(workout.nameOfTeacher());
        workoutEntity.setDescription(workout.description());
        workoutEntity.setWeekday(workout.weekday());
        workoutEntity.setUser(user);
        user.getWorkout().add(workoutEntity);
        this.userRepository.save(user);


        return UserMapper.UserToDTO(this.userRepository.getUserById(TypeRole.USER,userId));
    }

    @Override
    public UserDTO updateWorkout(Long workoutId,Long userId, WorkoutDTO<?> workout) {
        User user = this.userRepository.getUserById(TypeRole.USER,userId);
        log.info("user here {}" ,user);
        List<Workout> listWorkout = user.getWorkout().stream().toList();
        Workout workoutAlreadyExist = listWorkout.stream().filter(w -> w.getId().equals(workoutId)).findFirst().orElse(null);
        // logic to update workout and after save it in the good way my friend doesn't forget that please .
        workoutAlreadyExist.setName(workout.name());
        workoutAlreadyExist.setSeries(workout.series());
        workoutAlreadyExist.setDescription(workout.description());
        workoutAlreadyExist.setNameOfTeacher(workout.nameOfTeacher());
        workoutAlreadyExist.setWeekday(workout.weekday());
        workoutAlreadyExist.setUpdatedAt(LocalDateTime.now());
        workoutRepository.save(workoutAlreadyExist);

         return UserMapper.UserToDTO(this.userRepository.getUserById(TypeRole.USER,userId));


    }
}
