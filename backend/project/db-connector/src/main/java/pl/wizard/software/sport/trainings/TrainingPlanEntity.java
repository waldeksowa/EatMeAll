package pl.wizard.software.sport.trainings;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.members.MemberEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TRAINING_PLANS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanEntity extends AbstractBaseEntity {

    private LocalDate trainingPlanDate;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] trainings;
    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
