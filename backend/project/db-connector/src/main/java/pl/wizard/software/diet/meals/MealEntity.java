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
    private MealTimeEnum mealTime;
    private Integer prepareTime;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mealId")
    private Set<MealProductEntity> products;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mealId")
    private Set<StepEntity> steps;

    public enum MealTimeEnum {
        FAKE, BREAKFAST, SECOND_BREAKFAST, LUNCH, DINNER, SUPPER
    }
 }
