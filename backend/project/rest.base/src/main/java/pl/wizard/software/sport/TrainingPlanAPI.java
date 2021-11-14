package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.MemberService;
import pl.wizard.software.dto.CreateTrainingPlanDto;
import pl.wizard.software.dto.MemberDto;
import pl.wizard.software.dto.TrainingPlanDto;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.sport.trainings.TrainingPlanEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

import static pl.wizard.software.mapper.TrainingPlanDtoMapper.mapToTrainingPlanDto;
import static pl.wizard.software.mapper.TrainingPlanDtoMapper.mapToTrainingPlanDtos;

@RestController
@RequestMapping("/v2/trainingplans")
@Slf4j
@RequiredArgsConstructor
public class TrainingPlanAPI {

    private final LoginService loginService;
    private final MemberService memberService;
    private final TrainingPlanService trainingPlanService;


    @GetMapping("/member/{memberId}")
    public ResponseEntity<Collection<TrainingPlanDto>> getAll(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<MemberDto> member = memberService.findMemberById(accountId.get(), memberId);
        if (!member.isPresent()) {
            log.error("Member with id " + memberId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mapToTrainingPlanDtos(trainingPlanService.findAllByMember(accountId.get(), memberId)));
    }

    @GetMapping("/{trainingPlanId}/member/{memberId}")
    public ResponseEntity<TrainingPlanDto> getSpecified(@RequestHeader("Authorization") String token,
                                                                    @PathVariable Long trainingPlanId,
                                                                    @PathVariable Long memberId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<MemberDto> member = memberService.findMemberById(accountId.get(), memberId);
        if (!member.isPresent()) {
            log.error("Member with id " + memberId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        Optional<TrainingPlanEntity> trainingPlan = trainingPlanService.findByIdAndMember(accountId.get(), memberId, trainingPlanId);
        if (!trainingPlan.isPresent()) {
            log.error("Training plan with id " + trainingPlanId + " does not exists");
        }

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlan.get()));
    }

    @GetMapping("/member/{memberId}/current")
    public ResponseEntity<TrainingPlanDto> getCurrent(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<MemberDto> member = memberService.findMemberById(accountId.get(), memberId);
        if (!member.isPresent()) {
            log.error("Member with id " + memberId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        Optional<TrainingPlanEntity> trainingPlan = trainingPlanService.findCurrent(accountId.get(), memberId);
        if (!trainingPlan.isPresent()) {
            log.error("There is no training plan for current month");
        }

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlan.get()));
    }

    @GetMapping("/member/{memberId}/next")
    public ResponseEntity<TrainingPlanDto> getNext(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<MemberDto> member = memberService.findMemberById(accountId.get(), memberId);
        if (!member.isPresent()) {
            log.error("Member with id " + memberId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        Optional<TrainingPlanEntity> trainingPlan = trainingPlanService.findNext(accountId.get(), memberId);
        if (!trainingPlan.isPresent()) {
            log.error("There is no training plan for next month");
        }

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlan.get()));
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateTrainingPlanDto trainingPlan) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToTrainingPlanDto(trainingPlanService.save(trainingPlan)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingPlanDto> update(@RequestHeader("Authorization") String token,
                                              @PathVariable Long id,
                                              @Valid @RequestBody TrainingPlanDto training) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!trainingPlanService.findById(id).isPresent()) {
            log.error("Training with id = " + id + "does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlanService.update(training, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<TrainingPlanEntity> training = trainingPlanService.findById(id);
        if (!training.isPresent()) {
            log.error("Training plan with id = " + id + "does not exists");
            return ResponseEntity.badRequest().build();
        }

        trainingPlanService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
