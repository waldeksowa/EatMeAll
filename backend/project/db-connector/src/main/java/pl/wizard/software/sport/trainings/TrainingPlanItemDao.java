package pl.wizard.software.sport.trainings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanItemDao extends JpaRepository<TrainingPlanItemEntity, Long> {
}
