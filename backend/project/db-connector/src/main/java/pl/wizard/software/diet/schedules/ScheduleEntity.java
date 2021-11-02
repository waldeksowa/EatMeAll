package pl.wizard.software.diet.schedules;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
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
    private Long memberId;
}
