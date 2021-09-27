package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.ScheduleForDayDto;
import pl.wizard.software.diet.dto.ScheduleForWeekDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import java.io.*;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int DAYS_IN_WEEK = 7;
    private final MealDao mealRepository;
    private final ScheduleDao scheduleRepository;

    public Map<DayOfWeek, Map<MealTimeEnum, MealDto>> getScheduleByMealTime() {
        Map<DayOfWeek, Map<MealTimeEnum, MealDto>> schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new HashMap<>());
        }
        for (int i = 1; i < MealTimeEnum.values().length; i++) {
            List<MealEntity> meals = mealRepository.findRandomByMealTime(MealTimeEnum.values()[i].ordinal(), DAYS_IN_WEEK);
            for (DayOfWeek day : DayOfWeek.values()) {
                Optional<MealEntity> meal = meals.stream().findFirst();
                if (meal.isPresent()) {
                    schedule.get(day).put(MealTimeEnum.values()[i], MealDtoMapper.mapToMealDto(meal.get()));
                    meals.remove(meal);
                }
            }
        }
        return schedule;
    }

    public ScheduleForWeekDto createSchedule(CreateScheduleDto schedule) {
        Date scheduleDate = schedule.getSchedule().stream()
                .map(s -> s.getDate())
                .sorted()
                .findFirst()
                .get();

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberId(schedule.getMemberId())
                .scheduleDate(scheduleDate)
                .schedule(convertToBytes(schedule.getSchedule()))
                .build();

        return convertToScheduleForWeek(scheduleRepository.save(scheduleEntity));
    }

    @Transactional
    public List<ScheduleForWeekDto> findAll(Long accountId) {
        return scheduleRepository.findAll(accountId).stream()
                .map(scheduleEntity -> convertToScheduleForWeek(scheduleEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ScheduleForWeekDto> findById(Long accountId, Long scheduleId) {
        Optional<ScheduleEntity> schedule = scheduleRepository.findById(accountId, scheduleId);
        return getScheduleForWeekDto(schedule);
    }

    @Transactional
    public Optional<ScheduleForWeekDto> findByMember(Long accountId, Long memberId) {
        Optional<ScheduleEntity> schedule = scheduleRepository.findByMember(accountId, memberId);
        return getScheduleForWeekDto(schedule);
    }

    public ScheduleForWeekDto save(ScheduleForWeekDto schedule) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(schedule.getId());
        scheduleEntity.setCreatedAt(schedule.getCreatedAt());
        scheduleEntity.setUpdatedAt(new Date());
        scheduleEntity.setVersion(schedule.getVersion());
        scheduleEntity.setScheduleDate(schedule.getScheduleDate());
        scheduleEntity.setMemberId(schedule.getMemberId());
        scheduleEntity.setSchedule(convertToBytes(schedule.getSchedule()));

        return convertToScheduleForWeek(scheduleRepository.save(scheduleEntity));
    }

    public void deleteById(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    private Optional<ScheduleForWeekDto> getScheduleForWeekDto(Optional<ScheduleEntity> schedule) {
        if (schedule.isPresent()) {
            return Optional.of(convertToScheduleForWeek(schedule.get()));
        } else {
            return Optional.empty();
        }
    }

    private ScheduleForWeekDto convertToScheduleForWeek(ScheduleEntity scheduleEntity) {
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

    private byte[] convertToBytes(List<ScheduleForDayDto> schedule) {
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

    private List<ScheduleForDayDto> convertFromBytes(byte[] schedule) {
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
