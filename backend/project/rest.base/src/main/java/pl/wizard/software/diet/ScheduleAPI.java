package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.dto.CreateScheduleDto;
import pl.wizard.software.dto.MemberDto;
import pl.wizard.software.dto.ScheduleForWeekDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/v2/schedules")
@Slf4j
@RequiredArgsConstructor
public class ScheduleAPI {

    private final ScheduleService scheduleService;
    private final LoginService loginService;
    private final MealService mealService;
    private final MemberService memberService;

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
        ScheduleForWeekDto schedule = scheduleService.findByMember(accountId, id)
                .orElseThrow(() -> new NoSuchElementException("Could not find schedule with id " + id));

        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<ScheduleForWeekDto> findByMember(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        ScheduleForWeekDto schedule = scheduleService.findByMember(accountId, id)
                .orElseThrow(() -> new NoSuchElementException("Could not find schedule for member with id " + id));

        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/member/{memberId}/meal/{mealId}")
    public ResponseEntity<MealEntity> findMealByMember(@RequestHeader("Authorization") String token, @PathVariable Long memberId, @PathVariable Long mealId) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        MemberDto member = memberService.findMemberById(accountId, memberId)
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + memberId));
        MealEntity meal = mealService.findById(mealId)
                .orElseThrow(() -> new NoSuchElementException("Could not find meal with id " + mealId));

        return ResponseEntity.ok(scheduleService.customizeMeal(member, meal));
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

        return ResponseEntity.ok(scheduleService.save(accountId, id, schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        scheduleService.deleteById(accountId, id);
        return ResponseEntity.ok().build();
    }
}
