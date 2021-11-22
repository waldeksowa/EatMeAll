package pl.wizard.software.sport.trainings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingDao extends JpaRepository<TrainingEntity, Long> {
}
