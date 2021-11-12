package pl.wizard.software.sport.trainings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TRAININGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEntity extends AbstractBaseEntity {

    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "training_id")
    private Set<TrainingExerciseEntity> exercises;
    @Enumerated(value = EnumType.ORDINAL)
    private TrainingType trainingType;
    private Integer result;
    @Enumerated(value = EnumType.ORDINAL)
    private TrainingRating trainingRating;

    public enum TrainingType {
        AS_MANY_REPS_AS_POSSIBLE, FOR_TIME
    }

    public enum TrainingRating {
        EXCELLENT, GOOD, OK, POOR, WORST
    }

}
