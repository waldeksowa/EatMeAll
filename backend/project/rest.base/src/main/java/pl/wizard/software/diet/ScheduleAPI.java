package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wizard.software.diet.dto.MealDto;

import java.time.DayOfWeek;
import java.util.Map;

import static pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

@RestController
@RequestMapping("/v1/schedule")
@Slf4j
@RequiredArgsConstructor
public class ScheduleAPI {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Map<DayOfWeek, Map<MealTimeEnum, MealDto>>> getSchedule() {
        return ResponseEntity.ok(scheduleService.getScheduleByMealTime());
    }
}
