package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

@Getter
@Builder
public class MealDto {
    private Long id;
    private String name;
    private Set<MealTimeEnum> mealTime;
    private Double calorific;
    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double roughage;
}
