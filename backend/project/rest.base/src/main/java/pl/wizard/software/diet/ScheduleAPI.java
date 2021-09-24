package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.ScheduleForDayDto;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/v2/schedule")
@Slf4j
@RequiredArgsConstructor
public class ScheduleAPI {

    private final ScheduleService scheduleService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Map<DayOfWeek, Map<MealTimeEnum, MealDto>>> getSchedule() {
        return ResponseEntity.ok(scheduleService.getScheduleByMealTime());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody List<ScheduleForDayDto> schedule) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(schedule, accountId.get()));
    }
}
