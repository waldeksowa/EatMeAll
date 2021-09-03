package pl.wizard.software.diet.schedules;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;
import pl.wizard.software.diet.products.ProductEntity;

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
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
