package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    public static final int ONE_MEAL = 1;
    private final MealDao mealRepository;

    public Map<Day, Map<MealTimeEnum, MealDto>> getSchedule() {
        Map<Day, Map<MealTimeEnum, MealDto>> schedule = new HashMap<>();
        for (Day day : Day.values()) {
            Map<MealTimeEnum, MealDto> oneDayMeals = new HashMap<>();
            for (MealTimeEnum mealTime : MealTimeEnum.values()) {
                if (mealTime == MealTimeEnum.FAKE) {
                    continue;
                } else {
                    Optional<MealEntity> meal = mealRepository.findRandomByMealTime(mealTime.ordinal(), ONE_MEAL).stream().findFirst();
                    if (meal.isPresent()) {
                        oneDayMeals.put(mealTime, MealDtoMapper.mapToMealDto(meal.get()));
                    }
                }
            }
            schedule.put(day, oneDayMeals);
        }
        return schedule;
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

}
