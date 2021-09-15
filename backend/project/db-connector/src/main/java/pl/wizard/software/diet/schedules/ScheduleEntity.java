package pl.wizard.software.diet.schedules;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "SCHEDULES")
@Data
public class ScheduleEntity extends AbstractBaseEntity {

    @Enumerated(EnumType.ORDINAL)
    private MealTimeEnum mealTime;
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek mealDay;
    @OneToOne
    @JoinColumn(name = "meal_id")
    private MealEntity meal;
}
