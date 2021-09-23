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
    private double calorific;
    @Transient
    private double protein;
    @Transient
    private double fat;
    @Transient
    private double carbohydrates;
    @Transient
    private double roughage;

    @PostLoad
    void init() {
        for (MealProductEntity product : products) {
            double amountFactor = product.getAmount() / 100.0;
            calorific += product.getProduct().getCalorific() * amountFactor;
            protein += product.getProduct().getProtein() * amountFactor;
            fat += product.getProduct().getFat() * amountFactor;
            carbohydrates += product.getProduct().getCarbohydrates() * amountFactor;
            roughage += product.getProduct().getRoughage() * amountFactor;
        }

        calorific = new BigDecimal(calorific).setScale(1, RoundingMode.HALF_UP).doubleValue();
        protein = new BigDecimal(protein).setScale(1, RoundingMode.HALF_UP).doubleValue();
        fat = new BigDecimal(fat).setScale(1, RoundingMode.HALF_UP).doubleValue();
        carbohydrates = new BigDecimal(carbohydrates).setScale(1, RoundingMode.HALF_UP).doubleValue();
        roughage = new BigDecimal(roughage).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    public enum MealTimeEnum {
        FAKE, BREAKFAST, SECOND_BREAKFAST, LUNCH, DINNER, SUPPER
    }
 }
