package pl.wizard.software.sport.dto;

import lombok.Getter;

import static pl.wizard.software.sport.trainings.TrainingExerciseEntity.ExerciseType;

@Getter
public class CreateTrainingExerciseDto {

    private Long exerciseId;
    private ExerciseType exerciseType;
    private Integer amount;
}
