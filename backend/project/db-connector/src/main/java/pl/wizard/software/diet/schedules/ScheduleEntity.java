package pl.wizard.software.diet.schedules;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "SCHEDULES")
@Data
public class ScheduleEntity extends AbstractBaseEntity {

    private LocalDate mealDate;
    @Enumerated(EnumType.ORDINAL)
    private MealTimeEnum mealTime;
    private Long mealId;
    private Long memberId;
}
