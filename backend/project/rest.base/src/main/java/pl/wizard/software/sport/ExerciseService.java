package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.sport.exercises.ExerciseDao;
import pl.wizard.software.sport.exercises.ExerciseEntity;

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
}
