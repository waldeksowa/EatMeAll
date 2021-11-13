package pl.wizard.software.sport.dto;

import lombok.*;

import static pl.wizard.software.sport.trainings.TrainingExerciseEntity.ExerciseType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingExerciseDto {

    private Long trainingExerciseId;
    private Long exerciseId;
    private String exerciseName;
    private ExerciseType exerciseType;
    private Integer amount;
}
