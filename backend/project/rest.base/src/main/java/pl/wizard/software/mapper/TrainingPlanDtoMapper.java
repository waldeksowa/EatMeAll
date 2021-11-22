package pl.wizard.software.mapper;

import lombok.extern.slf4j.Slf4j;
import pl.wizard.software.dto.TrainingPlanDto;
import pl.wizard.software.dto.TrainingPlanItemDto;
import pl.wizard.software.sport.trainings.TrainingPlanEntity;

import java.io.*;
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
                .trainings(convertFromBytes(trainingPlanEntity.getTrainings()))
                .build();
    }

    public static List<TrainingPlanDto> mapToTrainingPlanDtos(List<TrainingPlanEntity> trainings) {
        return trainings.stream()
                .map(trainingPlanItemEntity -> mapToTrainingPlanDto(trainingPlanItemEntity))
                .collect(Collectors.toList());
    }

    public static byte[] convertToBytes(List<TrainingPlanItemDto> trainings) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(trainings);
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("Error when serialize training plan", e);
        }
        return null;
    }

    private static List<TrainingPlanItemDto> convertFromBytes(byte[] trainings) {
        ByteArrayInputStream bis = new ByteArrayInputStream(trainings);
        try {
            ObjectInputStream in = new ObjectInputStream(bis);
            return (List<TrainingPlanItemDto>)in.readObject();
        } catch (Exception e) {
            log.error("Error when deserialize training plan", e);
        }
        return null;
    }
}
