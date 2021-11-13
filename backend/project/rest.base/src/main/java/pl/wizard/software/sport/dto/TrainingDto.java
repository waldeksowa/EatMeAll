package pl.wizard.software.sport.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingRating;
import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingType;

@Getter
@Builder
public class TrainingDto {

    private Long id;
    private String name;
    private List<TrainingExerciseDto> exercises;
    private TrainingType trainingType;
    private Integer result;
    private TrainingRating trainingRating;
}
