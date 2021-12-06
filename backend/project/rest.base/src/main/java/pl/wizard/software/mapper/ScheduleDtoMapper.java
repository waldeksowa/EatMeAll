package pl.wizard.software.mapper;

import lombok.extern.slf4j.Slf4j;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.dto.ScheduleForWeekDto;
import pl.wizard.software.util.ByteConverter;

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
                .memberId(scheduleEntity.getMember().getId())
                .schedule(ByteConverter.convertFromBytes(scheduleEntity.getSchedule()))
                .build();
    }
}
