package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.sport.exercises.ExerciseEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v2/exercises")
@Slf4j
@RequiredArgsConstructor
public class ExerciseAPI {

    private final ExerciseService exerciseService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Collection<ExerciseEntity>> findAll(@RequestHeader("Authorization") String token) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(exerciseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseEntity> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        ExerciseEntity exercise = exerciseService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find exercise with id " + id));

        return ResponseEntity.ok(exercise);
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody ExerciseEntity exercise) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.save(exercise));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseEntity> update(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody ExerciseEntity exercise) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(exerciseService.update(exercise, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        exerciseService.delete(id);

        return ResponseEntity.ok().build();
    }
}
