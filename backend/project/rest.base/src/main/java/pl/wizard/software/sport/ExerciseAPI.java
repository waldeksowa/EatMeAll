package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.sport.exercises.ExerciseEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v2/exercises")
@Slf4j
@RequiredArgsConstructor
public class ExerciseAPI {

    private final ExerciseService exerciseService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Collection<ExerciseEntity>> findAll(@RequestHeader("Authorization") String token) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(exerciseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseEntity> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<ExerciseEntity> exercise = exerciseService.findById(id);
        if (!exercise.isPresent()) {
            log.error("Exercise with Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(exercise.get());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody ExerciseEntity exercise) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.save(exercise));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseEntity> update(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody ExerciseEntity exercise) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!exerciseService.findById(id).isPresent()) {
            log.error("Exercise with id = " + id + "does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(exerciseService.save(exercise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!exerciseService.findById(id).isPresent()) {
            log.error("Exercise with id = " + id + "does not exists");
            return ResponseEntity.badRequest().build();
        }
        exerciseService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
