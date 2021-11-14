package pl.wizard.software.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingRating;
import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingType;

@Getter
@Builder
public class TrainingPlanItemDto {

    private Long trainingItemId;
    private LocalDate trainingDate;
    private Long trainingId;
    private String trainingName;
    private TrainingType trainingType;
    private Integer trainingResult;
    private TrainingRating trainingRating;
}
