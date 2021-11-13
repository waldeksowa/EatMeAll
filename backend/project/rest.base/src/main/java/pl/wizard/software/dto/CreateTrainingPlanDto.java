package pl.wizard.software.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateTrainingPlanDto {

    private List<CreateTrainingItemDto> trainings;
    private Long memberId;
}
