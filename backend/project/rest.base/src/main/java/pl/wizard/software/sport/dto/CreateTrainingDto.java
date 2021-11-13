package pl.wizard.software.sport.dto;

import lombok.Builder;
import pl.wizard.software.sport.trainings.TrainingEntity;

import java.util.Set;

@Builder
public class CreateTrainingDto {

    private String name;
    private Set<TrainingExerciseDto> exercises;
    private TrainingEntity.TrainingType trainingType;
    private Integer result;
}
