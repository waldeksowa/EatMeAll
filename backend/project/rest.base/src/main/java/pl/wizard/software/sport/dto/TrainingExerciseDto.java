package pl.wizard.software.sport.dto;

import lombok.Builder;

import static pl.wizard.software.sport.trainings.TrainingExerciseEntity.ExerciseType;

@Builder
public class TrainingExerciseDto {

    private Long exerciseId;
    private ExerciseType exerciseType;
    private Integer amount;
}
