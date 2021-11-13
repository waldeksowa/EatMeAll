package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.sport.trainings.TrainingPlanDao;
import pl.wizard.software.sport.trainings.TrainingPlanItemDao;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingPlanService {

    private final TrainingPlanDao trainingPlanRepository;
    private final TrainingPlanItemDao trainingPlanItemRepository;

}
