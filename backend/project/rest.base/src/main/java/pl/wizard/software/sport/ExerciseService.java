package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.exception.ExerciseNotFoundException;
import pl.wizard.software.sport.exercises.ExerciseDao;
import pl.wizard.software.sport.exercises.ExerciseEntity;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseDao exerciseRepository;

    public List<ExerciseEntity> findAll() {
        return exerciseRepository.findAll();
    }

    public Optional<ExerciseEntity> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    public ExerciseEntity save(ExerciseEntity exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Transactional
    public ExerciseEntity update(ExerciseEntity exercise, Long id) {
        ExerciseEntity exerciseToUpdate = exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(id));
        exerciseToUpdate.setName(exercise.getName());
        exerciseToUpdate.setMusclePart(exercise.getMusclePart());
        exerciseToUpdate.setUpdatedAt(new Date());
        return save(exerciseToUpdate);
    }
}
