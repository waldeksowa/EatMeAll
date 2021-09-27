package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.dto.CreateScheduleDto;
import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.dto.ScheduleForWeekDto;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/v2/schedules")
@Slf4j
@RequiredArgsConstructor
public class ScheduleAPI {

    private final ScheduleService scheduleService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Collection<ScheduleForWeekDto>> findAll(@RequestHeader("Authorization") String token) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(scheduleService.findAll(accountId.get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleForWeekDto> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        Optional<ScheduleForWeekDto> stock = scheduleService.findById(accountId.get(), id);
        if (!stock.isPresent()) {
            log.error("Schedule with Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<ScheduleForWeekDto> findByMember(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        Optional<ScheduleForWeekDto> stock = scheduleService.findByMember(accountId.get(), id);
        if (!stock.isPresent()) {
            log.error("Schedule for member with Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @GetMapping("/random")
    public ResponseEntity<Map<DayOfWeek, Map<MealTimeEnum, MealDto>>> getSchedule() {
        return ResponseEntity.ok(scheduleService.getScheduleByMealTime());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateScheduleDto schedule) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleForWeekDto> update(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody ScheduleForWeekDto schedule) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!scheduleService.findById(accountId.get(), id).isPresent()) {
            log.error("Schedule with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(scheduleService.save(schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!scheduleService.findById(accountId.get(), id).isPresent()) {
            log.error("Schedule with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }
        scheduleService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
