package pl.wizard.software.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class TrainingPlanDto {

    private Long id;
    private LocalDate trainingPlanDate;
    private Long memberId;
    private List<TrainingPlanItemDto> trainings;
}
