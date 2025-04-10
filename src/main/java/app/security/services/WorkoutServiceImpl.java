package app.security.services;

import app.security.Enum.TypeRole;
import app.security.MapperDTO.UserMapper;
import app.security.MapperDTO.WorkoutMapper;
import app.security.domain.User;
import app.security.domain.Workout;
import app.security.dto.UserDTO;
import app.security.dto.WorkoutCreateDTO;
import app.security.dto.WorkoutDTO;
import app.security.dto.WorkoutUpdateDTO;
import app.security.exceptions.Exception;
import app.security.repository.UserRepository;
import app.security.repository.WorkoutRepository;
import app.security.services.validations.WorkoutUpdateValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final UserRepository userRepository;

    private final WorkoutRepository workoutRepository;
    @Override
    public List<WorkoutDTO<String>> getAllWorkoutFromUser(String username) {
        log.info("get all workout from user");

        List<WorkoutDTO<String>> listWorkoutDTO = new ArrayList<>();
        List<Workout> listWorkoutDomain = this.userRepository.getAllWorkoutFromUser(TypeRole.USER, username);


        listWorkoutDomain.forEach(w ->{
            WorkoutDTO<String> workoutDTO = WorkoutMapper.workoutToDTO(w);
            listWorkoutDTO.add(workoutDTO);
        });

        return listWorkoutDTO;
    }

    @Override
    public WorkoutDTO<String> getSpecificWorkoutFromUser(String username, Long workoutId) {
        List<Workout> listWorkout = this.userRepository.getAllWorkoutFromUser(TypeRole.USER,username);
        Workout workout =  listWorkout.stream().filter(w -> workoutId.equals(w.getId())).findFirst().orElse(null);

        if(workout == null) throw new Exception("couldn't find this workout",HttpStatus.NOT_FOUND);

        return WorkoutMapper.workoutToDTO(workout);
        // return this.workoutRepository.findById(workoutId).orElseThrow(() ->  new EntityNotFoundException("Workout not found"));
    }

    @Override
    public void deleteWorkoutById(UUID userId, Long workoutId) {

        User user = this.userRepository.getUserById(TypeRole.USER,userId);
        user.getWorkout().stream().filter(w -> w.getId().equals(workoutId)).findFirst().orElseThrow(()-> new app.security.exceptions.Exception("this workout doesn't exist",HttpStatus.NOT_FOUND));
        user.getWorkout().removeIf(w -> w.getId().equals(workoutId));
        this.userRepository.save(user);

    }

    @Override
    public UserDTO createWorkout(UUID userUid, WorkoutCreateDTO workout, String nameOfTeacher) {
        WorkoutDTO<User> workoutDto = new WorkoutDTO<>(null,workout.name(),workout.series(),workout.weekday(),workout.muscularGroup(), workout.description(),null,null,null,null);
        User user = this.userRepository.getUserById(TypeRole.USER,userUid);
        User userTeacher = this.userRepository.findByUsername(nameOfTeacher);
        Workout workoutEntity = WorkoutMapper.DtoToWorkout(workoutDto,userTeacher.getName(),user);
        user.getWorkout().add(workoutEntity);


        this.userRepository.save(user);


        return UserMapper.UserToDTO(this.userRepository.getUserById(TypeRole.USER,userUid));
    }

    @Override
    public UserDTO updateWorkout(Long workoutId, UUID userUid, WorkoutUpdateDTO workout, String nameOfTeacher) {
        log.info("workout User {}" , workout);

        User user = this.userRepository.getUserById(TypeRole.USER,userUid);
        if(user == null) throw new app.security.exceptions.Exception("user not found",HttpStatus.NOT_FOUND);
        User userTeacher = this.userRepository.findByUsername(nameOfTeacher);
        if(userTeacher == null) throw new app.security.exceptions.Exception("userTeacher not found",HttpStatus.NOT_FOUND);
        log.info("user here {}" ,user);
        List<Workout> listWorkout = user.getWorkout().stream().toList();
        Workout workoutAlreadyExist = listWorkout.stream().filter(w -> w.getId().equals(workoutId)).findFirst().orElse(null);
        // logic to update workout and after save it in the good way my friend doesn't forget that please .

        //remember to refactor this update.
        if(workoutAlreadyExist == null ) throw new Exception("Workout from user Not found", HttpStatus.NOT_FOUND);


        workoutRepository.save(WorkoutUpdateValidation.validationUpdate(workoutAlreadyExist,workout,userTeacher));

        return UserMapper.UserToDTO(this.userRepository.getUserById(TypeRole.USER,userUid));


    }
}
