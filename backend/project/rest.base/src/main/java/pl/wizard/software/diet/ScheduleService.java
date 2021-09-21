package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.MealWithTimeDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import java.time.DayOfWeek;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int DAYS_IN_WEEK = 7;
    private final MealDao mealRepository;

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

    public Set<ScheduleEntity> createSchedule(List<CreateScheduleDto> schedule, Long memberId) {
        Set<ScheduleEntity> scheduleToCreate = new HashSet<>();
        for (CreateScheduleDto createScheduleDto : schedule) {
            for (MealWithTimeDto meal : createScheduleDto.getMeals()) {
                Optional<MealEntity> mealRepositoryById = mealRepository.findById(meal.getMealId());
                if (!mealRepositoryById.isPresent()) {
                    log.error("Meal with Id " + meal.getMealId() + " does not exists");
                    return null;
                } else {
                    ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                            .mealDate(createScheduleDto.getMealDate())
                            .mealTime(MealTimeEnum.forName(meal.getMealTime()))
                            .mealId(meal.getMealId())
                            .memberId(memberId)
                            .build();
                    scheduleToCreate.add(scheduleEntity);
                }
            }
        }
        return scheduleToCreate;
    }
}
