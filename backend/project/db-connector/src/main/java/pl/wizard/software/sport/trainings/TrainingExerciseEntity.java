package pl.wizard.software.sport.trainings;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.sport.exercises.ExerciseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TRAINING_EXERCISES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingExerciseEntity extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "exercise_id")
    private ExerciseEntity exercise;
    @Enumerated(value = EnumType.ORDINAL)
    private ExerciseType exerciseType;
    private Integer amount;

    public enum ExerciseType {
        TIME, REPETITION
    }

}
