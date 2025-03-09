package app.security.services.validations;

import app.security.Enum.MuscularGroup;
import app.security.Enum.WeekDay;
import app.security.domain.User;
import app.security.domain.Workout;

import app.security.exceptions.Exception;
import app.security.dto.WorkoutUpdateDTO;

import java.time.LocalDateTime;

public class WorkoutUpdateValidation {

    public static Workout validationUpdate(Workout workoutAlreadyExist, WorkoutUpdateDTO workout, User userTeacher){
        boolean validateUpdate = false;
        if(workout.name() != null && !workout.name().isBlank() && workout.name().length() >= 3 && workout.name().length() <= 51){
            workoutAlreadyExist.setName(workout.name().trim());
            validateUpdate = true;
        }
        if(workout.series() != null && !workout.series().isBlank()){
            workoutAlreadyExist.setSeries(workout.series());
            validateUpdate = true;
        }
        if(workout.weekday() != null && !workout.weekday().isBlank()){
            var upperWorkoutWeekday = workout.weekday().toUpperCase();

            workoutAlreadyExist.setWeekday(WeekDay.valueOf(upperWorkoutWeekday));
            validateUpdate = true;
        }
        if(workout.muscularGroup() != null && !workout.muscularGroup().isBlank() ){

            var upperWorkoutMuscularGroup = workout.muscularGroup().toUpperCase();
            workoutAlreadyExist.setMuscularGroup(MuscularGroup.valueOf(upperWorkoutMuscularGroup));
            validateUpdate = true;
        }
        if(workout.description() != null && !workout.description().isBlank() ){
            workoutAlreadyExist.setDescription(workout.description());
            validateUpdate = true;
        }
        workoutAlreadyExist.setNameOfTeacher(userTeacher.getName());

        if(validateUpdate){
        workoutAlreadyExist.setUpdatedAt(LocalDateTime.now());
        }else{
            throw new Exception("nothing to update right here");
        }

        return workoutAlreadyExist;

    }

}
