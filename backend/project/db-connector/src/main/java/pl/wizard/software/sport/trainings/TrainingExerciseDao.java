package pl.wizard.software.sport.trainings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingExerciseDao extends JpaRepository<TrainingExerciseEntity, Long> {
}
