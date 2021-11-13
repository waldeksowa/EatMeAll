package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.dto.CreateTrainingDto;
import pl.wizard.software.dto.CreateTrainingExerciseDto;
import pl.wizard.software.dto.TrainingDto;
import pl.wizard.software.dto.TrainingExerciseDto;
import pl.wizard.software.sport.exercises.ExerciseDao;
import pl.wizard.software.sport.exercises.ExerciseEntity;
import pl.wizard.software.sport.trainings.TrainingDao;
import pl.wizard.software.sport.trainings.TrainingEntity;
import pl.wizard.software.sport.trainings.TrainingExerciseDao;
import pl.wizard.software.sport.trainings.TrainingExerciseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {

    private final TrainingDao trainingRepository;
    private final TrainingExerciseDao trainingExerciseRepository;
    private final ExerciseDao exerciseRepository;

    public List<TrainingEntity> findAll() {
        return trainingRepository.findAll();
    }

    public Optional<TrainingEntity> findById(Long id) {
        return trainingRepository.findById(id);
    }

    public TrainingEntity save(TrainingEntity training) {
        return trainingRepository.save(training);
    }

    public void deleteById(Long id) {
        trainingRepository.deleteById(id);
    }

    public TrainingEntity save(CreateTrainingDto training) {
        List<TrainingExerciseEntity> trainingExerciseEntities = new ArrayList<>();
        for (CreateTrainingExerciseDto trainingExercise : training.getExercises()) {
            Optional<ExerciseEntity> exercise = exerciseRepository.findById(trainingExercise.getExerciseId());
            if (!exercise.isPresent()) {
                log.error("Exercise with id " + trainingExercise.getExerciseId() + "does not exists");
            } else {
                TrainingExerciseEntity trainingExerciseEntity = TrainingExerciseEntity.builder()
                        .exercise(exercise.get())
                        .exerciseType(trainingExercise.getExerciseType())
                        .amount(trainingExercise.getAmount())
                        .build();
                trainingExerciseEntities.add(trainingExerciseEntity);
            }
        }

        TrainingEntity trainingEntity = TrainingEntity.builder()
                .name(training.getName())
                .exercises(trainingExerciseEntities)
                .trainingType(training.getTrainingType())
                .build();

        return save(trainingEntity);
    }

    public TrainingEntity update(TrainingDto training, Long id) {
        List<TrainingExerciseEntity> trainingExerciseEntities = new ArrayList<>();
        for (TrainingExerciseDto trainingExercise : training.getExercises()) {
            Optional<ExerciseEntity> exercise = exerciseRepository.findById(trainingExercise.getExerciseId());
            Optional<TrainingExerciseEntity> trainingExer = trainingExerciseRepository.findById(trainingExercise.getTrainingExerciseId());
            if (!exercise.isPresent()) {
                log.error("Exercise with id " + trainingExercise.getExerciseId() + "does not exists");
            } else if (!trainingExer.isPresent()) {
                log.error("Treining exercise with id " + trainingExercise.getTrainingExerciseId() + "does not exists");
            } else {
                TrainingExerciseEntity trainingExerciseEntity = trainingExer.get();
                trainingExerciseEntity.setExercise(exercise.get());
                trainingExerciseEntity.setExerciseType(trainingExercise.getExerciseType());
                trainingExerciseEntity.setAmount(trainingExercise.getAmount());
                trainingExerciseEntities.add(trainingExerciseEntity);
            }
        }

        TrainingEntity trainingEntity = trainingRepository.findById(id).get();
        trainingEntity.setName(training.getName());
        trainingEntity.setExercises(trainingExerciseEntities);
        trainingEntity.setTrainingType(training.getTrainingType());
        trainingEntity.setResult(training.getResult());
        trainingEntity.setTrainingRating(training.getTrainingRating());

        return save(trainingEntity);
    }

}
