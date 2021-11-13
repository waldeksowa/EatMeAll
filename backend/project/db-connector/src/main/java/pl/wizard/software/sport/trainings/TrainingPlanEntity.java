package pl.wizard.software.sport.trainings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.login.AccountEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TRAINING_PLANS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanEntity extends AbstractBaseEntity {

    private LocalDate trainingPlanDate;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "training_plan_id")
    private List<TrainingPlanItemEntity> trainings;
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
