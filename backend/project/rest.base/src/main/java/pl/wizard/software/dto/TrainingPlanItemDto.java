package pl.wizard.software.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingRating;
import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPlanItemDto {

    private LocalDate trainingDate;
    private Long trainingId;
    private String trainingName;
    private TrainingType trainingType;
    private Integer trainingResult;
    private TrainingRating trainingRating;
}
