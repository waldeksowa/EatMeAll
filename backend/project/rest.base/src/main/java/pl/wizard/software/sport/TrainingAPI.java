package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.sport.exercises.ExerciseEntity;
import pl.wizard.software.sport.trainings.TrainingEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v2/trainings")
@Slf4j
@RequiredArgsConstructor
public class TrainingAPI {

    private final TrainingService trainingService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Collection<TrainingEntity>> findAll(@RequestHeader("Authorization") String token) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(trainingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingEntity> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<TrainingEntity> training = trainingService.findById(id);
        if (!training.isPresent()) {
            log.error("Exercise with Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(training.get());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody TrainingEntity training) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(trainingService.save(training));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingEntity> update(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody TrainingEntity training) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!trainingService.findById(id).isPresent()) {
            log.error("Exercise with id = " + id + "does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(trainingService.save(training));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!trainingService.findById(id).isPresent()) {
            log.error("Exercise with id = " + id + "does not exists");
            return ResponseEntity.badRequest().build();
        }
        trainingService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
