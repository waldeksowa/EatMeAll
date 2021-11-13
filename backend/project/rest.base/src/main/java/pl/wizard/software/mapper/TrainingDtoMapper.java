package pl.wizard.software.mapper;

import pl.wizard.software.dto.TrainingDto;
import pl.wizard.software.dto.TrainingExerciseDto;
import pl.wizard.software.sport.trainings.TrainingEntity;
import pl.wizard.software.sport.trainings.TrainingExerciseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TrainingDtoMapper {

    private TrainingDtoMapper() {
    }

    public static List<TrainingDto> mapToTrainingDtos(List<TrainingEntity> trainings) {
        return trainings.stream()
                .map(trainingEntity -> mapToTrainingDto(trainingEntity))
                .collect(Collectors.toList());
    }

    public static TrainingDto mapToTrainingDto(TrainingEntity trainingEntity) {
        return TrainingDto.builder()
                .id(trainingEntity.getId())
                .name(trainingEntity.getName())
                .trainingType(trainingEntity.getTrainingType())
                .result(trainingEntity.getResult())
                .trainingRating(trainingEntity.getTrainingRating())
                .exercises(mapToTrainingExerciseDtos(trainingEntity.getExercises()))
                .build();
    }

    private static List<TrainingExerciseDto> mapToTrainingExerciseDtos(List<TrainingExerciseEntity> exercises) {
        return exercises.stream()
                .map(trainingExerciseEntity -> mapToTrainingExerciseDto(trainingExerciseEntity))
                .collect(Collectors.toList());
    }

    private static TrainingExerciseDto mapToTrainingExerciseDto(TrainingExerciseEntity trainingExerciseEntity) {
        return TrainingExerciseDto.builder()
                .trainingExerciseId(trainingExerciseEntity.getId())
                .exerciseType(trainingExerciseEntity.getExerciseType())
                .amount(trainingExerciseEntity.getAmount())
                .exerciseId(trainingExerciseEntity.getExercise().getId())
                .exerciseName(trainingExerciseEntity.getExercise().getName())
                .build();
    }
}
