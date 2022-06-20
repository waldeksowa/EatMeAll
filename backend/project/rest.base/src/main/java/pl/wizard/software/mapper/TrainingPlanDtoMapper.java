package pl.wizard.software.mapper;

import lombok.extern.slf4j.Slf4j;
import pl.wizard.software.dto.TrainingPlanDto;
import pl.wizard.software.sport.trainings.TrainingPlanEntity;
import pl.wizard.software.util.ByteConverter;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TrainingPlanDtoMapper {

    private TrainingPlanDtoMapper() {
    }

    public static TrainingPlanDto mapToTrainingPlanDto(TrainingPlanEntity trainingPlanEntity) {
        return TrainingPlanDto.builder()
                .id(trainingPlanEntity.getId())
                .createdAt(trainingPlanEntity.getCreatedAt())
                .updatedAt(trainingPlanEntity.getUpdatedAt())
                .version(trainingPlanEntity.getVersion())
                .trainingPlanDate(trainingPlanEntity.getTrainingPlanDate())
                .memberId(trainingPlanEntity.getMember().getId())
                .trainings(ByteConverter.convertFromBytes(trainingPlanEntity.getTrainings()))
                .build();
    }

    public static List<TrainingPlanDto> mapToTrainingPlanDtos(List<TrainingPlanEntity> trainings) {
        return trainings.stream()
                .map(trainingPlanItemEntity -> mapToTrainingPlanDto(trainingPlanItemEntity))
                .collect(Collectors.toList());
    }
}
