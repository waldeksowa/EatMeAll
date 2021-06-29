package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int ONE_DAY = 1;
    public static final int ONE_WEEK = 7;
    private final MealDao mealRepository;

    public Map<Day, Map<MealTimeEnum, MealDto>> getScheduleByDay() {
        Map<Day, Map<MealTimeEnum, MealDto>> schedule = new HashMap<>();
        for (Day day : Day.values()) {
            Map<MealTimeEnum, MealDto> oneDayMeals = new HashMap<>();
            addMealsForOneDay(oneDayMeals);
            schedule.put(day, oneDayMeals);
        }
        return schedule;
    }

    public Map<MealTimeEnum, Map<Day, MealDto>> getScheduleByMealTime() {
        Map<MealTimeEnum, Map<Day, MealDto>> schedule = new HashMap<>();
        for (MealTimeEnum mealTime : MealTimeEnum.values()) {
            if (mealTime == MealTimeEnum.FAKE) {
                continue;
            } else {
                List<MealEntity> meals = mealRepository.findRandomByMealTime(mealTime.ordinal(), ONE_WEEK);
                Map<Day, MealDto> oneTimeMeals = new HashMap<>();
                addMealsForOneMealTime(meals, oneTimeMeals);
                schedule.put(mealTime, oneTimeMeals);
            }
        }
        return schedule;
    }

    private void addMealsForOneDay(Map<MealTimeEnum, MealDto> oneDayMeals) {
        for (MealTimeEnum mealTime : MealTimeEnum.values()) {
            if (mealTime == MealTimeEnum.FAKE) {
                continue;
            } else {
                Optional<MealEntity> meal = mealRepository.findRandomByMealTime(mealTime.ordinal(), ONE_DAY).stream().findFirst();
                if (meal.isPresent()) {
                    oneDayMeals.put(mealTime, MealDtoMapper.mapToMealDto(meal.get()));
                }
            }
        }
    }

    private void addMealsForOneMealTime(List<MealEntity> meals, Map<Day, MealDto> oneTimeMeals) {
        for (Day day : Day.values()) {
            Optional<MealEntity> meal = meals.stream().findFirst();
            if (meal.isPresent()) {
                oneTimeMeals.put(day, MealDtoMapper.mapToMealDto(meal.get()));
                meals.remove(meal);
            }
        }
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

}
