package pl.wizard.software.diet.schedules;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SCHEDULES")
@Data
public class ScheduleEntity extends AbstractBaseEntity {

    private LocalDate mealDate;
    @Enumerated(EnumType.ORDINAL)
    private MealTimeEnum mealTime;
    @Column(name = "meal_id")
    private Long mealId;
    @Column(name = "member_id")
    private Long memberId;
}
