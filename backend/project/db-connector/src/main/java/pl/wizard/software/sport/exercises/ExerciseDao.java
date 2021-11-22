package pl.wizard.software.sport.exercises;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseDao extends JpaRepository<ExerciseEntity, Long> {
}
