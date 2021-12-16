package pl.wizard.software.diet.schedules;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.members.MemberEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SCHEDULES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity extends AbstractBaseEntity {

    private LocalDate scheduleDate;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] schedule;
    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
