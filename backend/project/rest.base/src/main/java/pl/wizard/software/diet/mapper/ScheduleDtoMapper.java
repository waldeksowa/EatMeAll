package pl.wizard.software.diet.mapper;

import lombok.extern.slf4j.Slf4j;
import pl.wizard.software.diet.dto.ScheduleForDayDto;
import pl.wizard.software.diet.dto.ScheduleForWeekDto;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ScheduleDtoMapper {
    private ScheduleDtoMapper() {
    }

    public static Set<ScheduleForWeekDto> mapToScheduleDtos(Set<ScheduleEntity> schedules) {
        return schedules.stream()
                .map(schedule -> mapToScheduleDto(schedule))
                .collect(Collectors.toSet());

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

    public static ScheduleEntity mapToScheduleEntity(ScheduleForWeekDto schedule) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(schedule.getId());
        scheduleEntity.setCreatedAt(schedule.getCreatedAt());
        scheduleEntity.setUpdatedAt(new Date());
        scheduleEntity.setVersion(schedule.getVersion());
        scheduleEntity.setScheduleDate(schedule.getScheduleDate());
        scheduleEntity.setMemberId(schedule.getMemberId());
        scheduleEntity.setSchedule(convertToBytes(schedule.getSchedule()));
        return scheduleEntity;
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
