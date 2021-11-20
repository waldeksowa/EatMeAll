package pl.wizard.software.sport.trainings;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TRAININGS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEntity extends AbstractBaseEntity {

    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "training_id")
    private List<TrainingExerciseEntity> exercises;
    @Enumerated(value = EnumType.ORDINAL)
    private TrainingType trainingType;
    private Integer repetitionResult;
    private Double timeWeightResult;
    @Enumerated(value = EnumType.ORDINAL)
    private TrainingRating trainingRating;

    public enum TrainingType {
        AS_MANY_REPS_AS_POSSIBLE, FOR_TIME
    }

    public enum TrainingRating {
        EXCELLENT, GOOD, OK, POOR, WORST
    }

}
