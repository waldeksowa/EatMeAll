package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.CreateTrainingDto;
import pl.wizard.software.dto.TrainingDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.sport.trainings.TrainingEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

import static pl.wizard.software.mapper.TrainingDtoMapper.mapToTrainingDto;
import static pl.wizard.software.mapper.TrainingDtoMapper.mapToTrainingDtos;

@RestController
@RequestMapping("/v2/trainings")
@Slf4j
@RequiredArgsConstructor
public class TrainingAPI {

    private final TrainingService trainingService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Collection<TrainingDto>> findAll(@RequestHeader("Authorization") String token) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(mapToTrainingDtos(trainingService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        TrainingEntity training = trainingService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find training with id " + id));

        return ResponseEntity.ok(mapToTrainingDto(training));
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateTrainingDto training) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToTrainingDto(trainingService.save(training)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> update(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody TrainingDto training) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        TrainingEntity trainingEntity = trainingService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find training with id " + id));

        return ResponseEntity.ok(mapToTrainingDto(trainingService.update(training, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long account = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        TrainingEntity trainingEntity = trainingService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find training with id " + id));
        trainingService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
