package pl.wizard.software.mapper;

import pl.wizard.software.dto.TrainingPlanDto;
import pl.wizard.software.dto.TrainingPlanItemDto;
import pl.wizard.software.sport.trainings.TrainingPlanEntity;
import pl.wizard.software.sport.trainings.TrainingPlanItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TrainingPlanDtoMapper {

    private TrainingPlanDtoMapper() {
    }

    public static TrainingPlanDto mapToTrainingPlanDto(TrainingPlanEntity trainingPlanEntity) {
        return TrainingPlanDto.builder()
                .id(trainingPlanEntity.getId())
                .trainingPlanDate(trainingPlanEntity.getTrainingPlanDate())
                .memberId(trainingPlanEntity.getMember().getId())
                .trainings(mapToTrainingPlanItemDtos(trainingPlanEntity.getTrainings()))
                .build();
    }

    public static List<TrainingPlanDto> mapToTrainingPlanDtos(List<TrainingPlanEntity> trainings) {
        return trainings.stream()
                .map(trainingPlanItemEntity -> mapToTrainingPlanDto(trainingPlanItemEntity))
                .collect(Collectors.toList());
    }

    private static List<TrainingPlanItemDto> mapToTrainingPlanItemDtos(List<TrainingPlanItemEntity> trainings) {
        return trainings.stream()
                .map(trainingPlanItemEntity -> mapToTrainingPlanItemDto(trainingPlanItemEntity))
                .collect(Collectors.toList());
    }

    public static TrainingPlanItemDto mapToTrainingPlanItemDto(TrainingPlanItemEntity trainingPlanItemEntity) {
        return TrainingPlanItemDto.builder()
                .trainingItemId(trainingPlanItemEntity.getId())
                .trainingDate(trainingPlanItemEntity.getTrainingDate())
                .trainingId(trainingPlanItemEntity.getTraining().getId())
                .trainingName(trainingPlanItemEntity.getTraining().getName())
                .trainingType(trainingPlanItemEntity.getTraining().getTrainingType())
                .trainingResult(trainingPlanItemEntity.getTraining().getResult())
                .trainingRating(trainingPlanItemEntity.getTraining().getTrainingRating())
                .build();
    }
}
