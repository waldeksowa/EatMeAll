package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.sport.trainings.TrainingDao;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingDao trainingRepository;

}
