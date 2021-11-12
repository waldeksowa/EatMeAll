package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.sport.trainings.TrainingDao;
import pl.wizard.software.sport.trainings.TrainingEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingDao trainingRepository;

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
}
