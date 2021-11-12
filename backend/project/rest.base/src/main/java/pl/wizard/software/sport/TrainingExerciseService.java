package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingExerciseService {

    private final TrainingExerciseDao trainingExerciseRepository;


}
