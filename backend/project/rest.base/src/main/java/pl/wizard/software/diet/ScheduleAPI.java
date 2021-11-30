package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.CreateScheduleDto;
import pl.wizard.software.dto.ScheduleForWeekDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.util.Collection;
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
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(scheduleService.findAll(accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleForWeekDto> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        Optional<ScheduleForWeekDto> stock = scheduleService.findById(accountId, id);
        if (!stock.isPresent()) {
            log.error("Schedule with Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<ScheduleForWeekDto> findByMember(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        Optional<ScheduleForWeekDto> schedule = scheduleService.findByMember(accountId, id);
        if (!schedule.isPresent()) {
            log.error("Schedule for member with Id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(schedule.get());
    }

    @GetMapping("/random")
    public ResponseEntity<ScheduleForWeekDto> getSchedule() {
        return ResponseEntity.ok(scheduleService.getScheduleByMealTime());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateScheduleDto schedule) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleForWeekDto> update(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody ScheduleForWeekDto schedule) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        if (!scheduleService.findById(accountId, id).isPresent()) {
            log.error("Schedule with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(scheduleService.save(schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        if (!scheduleService.findById(accountId, id).isPresent()) {
            log.error("Schedule with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }
        scheduleService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
