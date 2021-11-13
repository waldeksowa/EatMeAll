package pl.wizard.software.sport.dto;

import lombok.Getter;

import java.util.List;

import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingType;

@Getter
public class CreateTrainingDto {

    private String name;
    private List<CreateTrainingExerciseDto> exercises;
    private TrainingType trainingType;
}
