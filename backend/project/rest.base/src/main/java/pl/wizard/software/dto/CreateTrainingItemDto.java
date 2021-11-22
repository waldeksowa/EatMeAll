package pl.wizard.software.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateTrainingItemDto {

    private LocalDate trainingDate;
    private Long trainingId;
}
