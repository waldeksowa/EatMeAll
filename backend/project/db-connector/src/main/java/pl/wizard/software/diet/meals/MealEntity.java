package pl.wizard.software.diet.meals;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MEALS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealEntity extends AbstractBaseEntity {

    private String name;
    private String author;
    private String description;
    @ElementCollection(targetClass = MealTimeEnum.class)
    @CollectionTable(name="meal_time")
    @Enumerated(EnumType.ORDINAL)
    private Set<MealTimeEnum> mealTime;
    private int prepareTime;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "meal_id")
    private Set<MealProductEntity> products;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "meal_id")
    @OrderBy("position")
    private List<StepEntity> steps;

    @Transient
    private Double calorific;
    @Transient
    private Double protein;
    @Transient
    private Double fat;
    @Transient
    private Double carbohydrates;
    @Transient
    private Double roughage;

    @PostLoad
    private void init() {
        calorific = new BigDecimal(products.stream().mapToDouble(p -> p.getProduct().getCalorific()).sum()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        protein = new BigDecimal(products.stream().mapToDouble(p -> p.getProduct().getProtein()).sum()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        fat = new BigDecimal(products.stream().mapToDouble(p -> p.getProduct().getFat()).sum()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        carbohydrates = new BigDecimal(products.stream().mapToDouble(p -> p.getProduct().getCarbohydrates()).sum()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        roughage = new BigDecimal(products.stream().mapToDouble(p -> p.getProduct().getRoughage()).sum()).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    public enum MealTimeEnum {
        FAKE, BREAKFAST, SECOND_BREAKFAST, LUNCH, DINNER, SUPPER
    }
 }
