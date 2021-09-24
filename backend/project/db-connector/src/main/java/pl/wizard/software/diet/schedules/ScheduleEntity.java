package pl.wizard.software.diet.schedules;

import lombok.Builder;
import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "SCHEDULES")
@Data
@Builder
public class ScheduleEntity extends AbstractBaseEntity {

    private LocalDate scheduleDate;
    @Lob
    @Column(name = "schedule", columnDefinition = "BLOB")
    private byte[] schedule;
    @Column(name = "member_id")
    private Long memberId;
}
