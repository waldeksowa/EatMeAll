package pl.wizard.software.diet.meals;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MEALS")
@Data
public class MealEntity extends AbstractBaseEntity {
    private String name;
    private String author;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private mealTimeEnum mealTime;
    private Integer prepareTime;
    @OneToMany
    @JoinColumn(name = "meal_id")
    private Set<MealProductEntity> products;
    @OneToMany
    @JoinColumn(name = "meal_id")
    private Set<StepEntity> steps;

    public enum mealTimeEnum {
        FAKE, BREAKFAST, SECOND_BREAKFAST, LUNCH, DINNER, SUPPER
    }
}
