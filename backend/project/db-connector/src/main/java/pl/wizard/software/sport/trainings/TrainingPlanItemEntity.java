package pl.wizard.software.sport.trainings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "TRAINING_PLAN_ITEMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanItemEntity extends AbstractBaseEntity {

    private LocalDate trainingDate;
    @OneToOne
    @JoinColumn(name = "training_id")
    private TrainingEntity training;
}
