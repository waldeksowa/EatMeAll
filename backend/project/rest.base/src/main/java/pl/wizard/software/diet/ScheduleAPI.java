package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.meals.MealTimeEnum;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;


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

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody List<CreateScheduleDto> schedule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(schedule));
    }
}
