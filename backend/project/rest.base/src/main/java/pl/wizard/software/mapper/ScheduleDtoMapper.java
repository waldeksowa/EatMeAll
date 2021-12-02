package pl.wizard.software.mapper;

import lombok.extern.slf4j.Slf4j;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.dto.ScheduleForDayDto;
import pl.wizard.software.dto.ScheduleForWeekDto;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ScheduleDtoMapper {
    private ScheduleDtoMapper() {
    }

    public static List<ScheduleForWeekDto> mapToScheduleDtos(List<ScheduleEntity> schedules) {
        return schedules.stream()
                .map(schedule -> mapToScheduleDto(schedule))
                .collect(Collectors.toList());

    }

    public static ScheduleForWeekDto mapToScheduleDto(ScheduleEntity scheduleEntity) {
        return ScheduleForWeekDto.builder()
                .Id(scheduleEntity.getId())
                .createdAt(scheduleEntity.getCreatedAt())
                .updatedAt(scheduleEntity.getUpdatedAt())
                .version(scheduleEntity.getVersion())
                .scheduleDate(scheduleEntity.getScheduleDate())
                .memberId(scheduleEntity.getMemberId())
                .schedule(convertFromBytes(scheduleEntity.getSchedule()))
                .build();
    }

    public static byte[] convertToBytes(List<ScheduleForDayDto> schedule) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(schedule);
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("Error when serialize schedule", e);
        }
        return null;
    }

    private static List<ScheduleForDayDto> convertFromBytes(byte[] schedule) {
        ByteArrayInputStream bis = new ByteArrayInputStream(schedule);
        try {
            ObjectInputStream in = new ObjectInputStream(bis);
            return (List<ScheduleForDayDto>)in.readObject();
        } catch (Exception e) {
            log.error("Error when deserialize schedule", e);
        }
        return null;
    }
}
