package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int ONE_WEEK = 7;
    private final MealDao mealRepository;

    public Map<DayOfWeek, Map<MealTimeEnum, MealDto>> getScheduleByMealTime() {
        Map<DayOfWeek, Map<MealTimeEnum, MealDto>> schedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            schedule.put(day, new HashMap<>());
        }
        for (MealTimeEnum mealTime : MealTimeEnum.values()) {
            if (mealTime == MealTimeEnum.FAKE) {
                continue;
            } else {
                List<MealEntity> meals = mealRepository.findRandomByMealTime(mealTime.ordinal(), ONE_WEEK);
                for (DayOfWeek day : DayOfWeek.values()) {
                    Optional<MealEntity> meal = meals.stream().findFirst();
                    if (meal.isPresent()) {
                        schedule.get(day).put(mealTime, MealDtoMapper.mapToMealDto(meal.get()));
                        meals.remove(meal);
                    }
                }
            }
        }
        return schedule;
    }
}
