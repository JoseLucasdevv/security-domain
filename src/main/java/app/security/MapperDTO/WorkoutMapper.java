package app.security.MapperDTO;

import app.security.Enum.MuscularGroup;
import app.security.domain.User;
import app.security.domain.Workout;
import app.security.types.WorkoutDTO;


public class WorkoutMapper {


    public static Workout DtoToWorkout(WorkoutDTO<User> workoutDTO){
        Workout workout = new Workout();

        workout.setDescription(workoutDTO.description());
        workout.setName(workoutDTO.name());
        workout.setSeries(workoutDTO.series());
        workout.setWeekday(workoutDTO.weekday());
        workout.setMuscularGroup(workoutDTO.muscularGroup());
        workout.setNameOfTeacher(workoutDTO.nameOfTeacher());
        workout.setUser(workoutDTO.user());
        return workout;
    }


    public static Workout DtoToWorkout(WorkoutDTO<User> workoutDTO,String userTeacher,User user){
        Workout workout = new Workout();

        workout.setDescription(workoutDTO.description());
        workout.setName(workoutDTO.name());
        workout.setSeries(workoutDTO.series());
        workout.setWeekday(workoutDTO.weekday());
        workout.setMuscularGroup(workoutDTO.muscularGroup());
        workout.setNameOfTeacher(userTeacher);
        workout.setUser(user);
        return workout;
    }


    public static WorkoutDTO<?> workoutToDTO(Workout workout){


        return new WorkoutDTO<>(workout.getId()
                ,workout.getName()
                ,workout.getSeries()
                ,workout.getWeekday()
                ,workout.getMuscularGroup()
                ,workout.getDescription()
                ,workout.getNameOfTeacher()
                ,workout.getUser().getName()
                ,workout.getCreatedAt()
                ,workout.getUpdatedAt());



    }


}
