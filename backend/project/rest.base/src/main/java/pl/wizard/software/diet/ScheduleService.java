package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public ScheduleForWeekDto createSchedule(List<ScheduleForDayDto> schedule, Long memberId) {
        LocalDate scheduleDate = schedule.stream()
                .map(s -> s.getDate())
                .sorted()
                .findFirst()
                .get();

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberId(memberId)
                .date(scheduleDate)
                .schedule(convertToBytes(schedule))
                .build();

        return convertToScheduleForWeek(scheduleRepository.save(scheduleEntity));
    }

    public List<ScheduleForWeekDto> findAllSchedules(Long accountId) {
        return scheduleRepository.findAllSchedules(accountId).stream()
                .map(scheduleEntity -> convertToScheduleForWeek(scheduleEntity))
                .collect(Collectors.toList());
    }

    public Optional<ScheduleForWeekDto> findById(Long accountId, Long scheduleId) {
        Optional<ScheduleEntity> schedule = scheduleRepository.findById(accountId, scheduleId);
        if (schedule.isPresent()) {
            return Optional.of(convertToScheduleForWeek(schedule.get()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<ScheduleForWeekDto> findByMember(Long accountId, Long memberId) {
        Optional<ScheduleEntity> schedule = scheduleRepository.findByMember(accountId, memberId);
        if (schedule.isPresent()) {
            return Optional.of(convertToScheduleForWeek(schedule.get()));
        } else {
            return Optional.empty();
        }
    }

    private ScheduleForWeekDto convertToScheduleForWeek(ScheduleEntity scheduleEntityAfterSave) {
        return ScheduleForWeekDto.builder()
                .scheduleDate(scheduleEntityAfterSave.getDate())
                .memberId(scheduleEntityAfterSave.getMemberId())
                .schedule(convertFromBytes(scheduleEntityAfterSave.getSchedule()))
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
