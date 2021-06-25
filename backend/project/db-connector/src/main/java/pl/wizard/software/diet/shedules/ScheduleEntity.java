package pl.wizard.software.diet.shedules;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.MealEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SHEDULES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity extends AbstractBaseEntity {

    @ManyToMany
    private Set<MealEntity> meals;
    @ElementCollection(targetClass = DayEnum.class)
    @CollectionTable(name="schedule_day")
    @Enumerated(EnumType.ORDINAL)
    private Set<DayEnum> day;

    public enum DayEnum{
        FAKE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}

