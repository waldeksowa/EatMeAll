package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.CreateTrainingPlanDto;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v2/trainingplans")
@Slf4j
@RequiredArgsConstructor
public class TrainingPlanAPI {

    private final LoginService loginService;
    private final TrainingPlanService trainingPlanService;

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateTrainingPlanDto training) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(trainingPlanService.save(training));
    }
}
