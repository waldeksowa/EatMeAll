package pl.wizard.software.diet.schedules;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SCHEDULES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity extends AbstractBaseEntity {

    private Date scheduleDate;
    @Lob
    @Column(name = "schedule", columnDefinition = "BLOB")
    private byte[] schedule;
    private Long memberId;
}
