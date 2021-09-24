package pl.wizard.software.diet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.ScheduleForDayDto;
import pl.wizard.software.diet.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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

    public ScheduleEntity createSchedule(List<ScheduleForDayDto> schedule, Long memberId) {
        LocalDate scheduleDate = schedule.stream()
                .map(s -> s.getDate())
                .sorted()
                .findFirst()
                .get();

        try {
            byte[] data = new ObjectMapper().writeValueAsBytes(schedule);
            ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                    .memberId(memberId)
                    .scheduleDate(scheduleDate)
                    .schedule(data)
                    .build();
            return scheduleRepository.save(scheduleEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

}
