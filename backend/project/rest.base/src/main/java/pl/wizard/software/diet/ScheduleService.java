package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.ScheduleForWeekDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.mapper.ScheduleDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;

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
                .schedule(ScheduleDtoMapper.convertToBytes(schedule.getSchedule()))
                .build();

        return ScheduleDtoMapper.mapToScheduleDto(scheduleRepository.save(scheduleEntity));
    }

    @Transactional
    public List<ScheduleForWeekDto> findAll(Long accountId) {
        return scheduleRepository.findAll(accountId).stream()
                .map(scheduleEntity -> ScheduleDtoMapper.mapToScheduleDto(scheduleEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ScheduleForWeekDto> findById(Long accountId, Long scheduleId) {
        Optional<ScheduleEntity> schedule = scheduleRepository.findById(accountId, scheduleId);
        return getScheduleForWeekDto(schedule);
    }

    @Transactional
    public Optional<ScheduleForWeekDto> findByMember(Long accountId, Long memberId) {
        Pageable topOne = PageRequest.of(1, 1);
        Optional<ScheduleEntity> schedule = scheduleRepository.findByMember(accountId, memberId, topOne).stream().findFirst();
        return getScheduleForWeekDto(schedule);
    }

    public ScheduleForWeekDto save(ScheduleForWeekDto schedule) {
        ScheduleEntity scheduleEntity = ScheduleDtoMapper.mapToScheduleEntity(schedule);
        return ScheduleDtoMapper.mapToScheduleDto(scheduleRepository.save(scheduleEntity));
    }

    public void deleteById(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    private Optional<ScheduleForWeekDto> getScheduleForWeekDto(Optional<ScheduleEntity> schedule) {
        if (schedule.isPresent()) {
            return Optional.of(ScheduleDtoMapper.mapToScheduleDto(schedule.get()));
        } else {
            return Optional.empty();
        }
    }

}
