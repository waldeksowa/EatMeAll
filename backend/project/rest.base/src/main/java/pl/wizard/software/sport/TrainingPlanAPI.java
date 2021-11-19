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
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.exception.MemberNotFoundException;
import pl.wizard.software.exception.TrainingPlanNotFoundException;
import pl.wizard.software.sport.trainings.TrainingPlanEntity;

import javax.validation.Valid;
import java.util.Collection;

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
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        MemberDto member = memberService.findMemberById(accountId, memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        return ResponseEntity.ok(mapToTrainingPlanDtos(trainingPlanService.findAllByMember(accountId, memberId)));
    }

    @GetMapping("/{trainingPlanId}/member/{memberId}")
    public ResponseEntity<TrainingPlanDto> getSpecified(@RequestHeader("Authorization") String token,
                                                                    @PathVariable Long trainingPlanId,
                                                                    @PathVariable Long memberId) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        MemberDto member = memberService.findMemberById(accountId, memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        TrainingPlanEntity trainingPlan = trainingPlanService.findByIdAndMember(accountId, memberId, trainingPlanId)
                .orElseThrow(TrainingPlanNotFoundException::new);

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlan));
    }

    @GetMapping("/member/{memberId}/current")
    public ResponseEntity<TrainingPlanDto> getCurrent(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        MemberDto member = memberService.findMemberById(accountId, memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        TrainingPlanEntity trainingPlan = trainingPlanService.findCurrent(accountId, memberId)
                .orElseThrow(TrainingPlanNotFoundException::new);

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlan));
    }

    @GetMapping("/member/{memberId}/next")
    public ResponseEntity<TrainingPlanDto> getNext(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        MemberDto member = memberService.findMemberById(accountId, memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        TrainingPlanEntity trainingPlan = trainingPlanService.findNext(accountId, memberId)
                .orElseThrow(TrainingPlanNotFoundException::new);

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlan));
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateTrainingPlanDto trainingPlan) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToTrainingPlanDto(trainingPlanService.save(trainingPlan)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingPlanDto> update(@RequestHeader("Authorization") String token,
                                              @PathVariable Long id,
                                              @Valid @RequestBody TrainingPlanDto training) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        trainingPlanService.findById(id)
                .orElseThrow(TrainingPlanNotFoundException::new);

        return ResponseEntity.ok(mapToTrainingPlanDto(trainingPlanService.update(training, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        trainingPlanService.findById(id)
                .orElseThrow(TrainingPlanNotFoundException::new);

        trainingPlanService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
