package pl.wizard.software.dto;

import lombok.*;

import java.util.List;

import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingRating;
import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {

    private Long id;
    private String name;
    private List<TrainingExerciseDto> exercises;
    private TrainingType trainingType;
    private Integer result;
    private TrainingRating trainingRating;
}
