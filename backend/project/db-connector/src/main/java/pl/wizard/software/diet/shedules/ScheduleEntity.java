package pl.wizard.software.diet.shedules;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.MealEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MEALS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Set<MealEntity> meals;
    @Enumerated(EnumType.ORDINAL)
    private DayEnum day;

    public enum DayEnum{
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}

