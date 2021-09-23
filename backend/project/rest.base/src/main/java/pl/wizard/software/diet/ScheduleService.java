package pl.wizard.software.diet;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.ScheduleForDayDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


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

    public ScheduleEntity createSchedule(List<CreateScheduleDto> schedule, Long memberId) {
        List<ScheduleForDayDto> scheduleForWeek = new ArrayList<>();
        LocalDate scheduleDate = schedule.stream()
                .map(s -> s.getDate())
                .sorted()
                .findFirst()
                .get();
        for (CreateScheduleDto createScheduleDto : schedule) {
            ScheduleForDayDto scheduleForDay = new ScheduleForDayDto();
            for (ScheduleForDayDto meal : createScheduleDto.getMeals()) {
                fillMeals(scheduleForDay, meal);
            }
            scheduleForWeek.add(scheduleForDay);
        }
        Gson gson = new Gson();
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .memberId(memberId)
                .date(scheduleDate)
                .schedule(gson.toJson(scheduleForWeek))
                .build();

        return scheduleRepository.save(scheduleEntity);
    }

    private void fillMeals(ScheduleForDayDto scheduleForDay, ScheduleForDayDto meal) {
        if (meal.getBreakfast() != null) {
            if (isMealExists(meal.getBreakfast())) {
                scheduleForDay.setBreakfast(meal.getBreakfast());
            }
        }
        if (meal.getSecondBreakfast() != null) {
            if (isMealExists(meal.getSecondBreakfast())) {
                scheduleForDay.setSecondBreakfast(meal.getSecondBreakfast());
            }
        }
        if (meal.getLunch() != null) {
            if (isMealExists(meal.getLunch())) {
                scheduleForDay.setLunch(meal.getLunch());
            }
        }
        if (meal.getDinner() != null) {
            if (isMealExists(meal.getDinner())) {
                scheduleForDay.setDinner(meal.getDinner());
            }
        }
        if (meal.getSupper() != null) {
            if (isMealExists(meal.getSupper())) {
                scheduleForDay.setSupper(meal.getSupper());
            }
        }
    }

    private boolean isMealExists(Long mealId) {
        Optional<MealEntity> meal = mealRepository.findById(mealId);
        if (meal.isPresent()) {
            return true;
        } else {
            log.error("Meal with Id " + mealId + " does not exists");
            return false;
        }
    }
}
