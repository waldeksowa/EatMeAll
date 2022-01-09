package pl.wizard.software.dto;

import lombok.Builder;
import lombok.Getter;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.meals.StepEntity;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class MemberMealDto {
    private Long id;
    private String name;
    private String author;
    private String description;
    private Set<MealTimeEnum> mealTime;
    private int prepareTime;
    private Set<MealProductEntity> products;
    private List<StepEntity> steps;
    private double calorific;
    private double protein;
    private double fat;
    private double carbohydrates;
    private double roughage;
    private Long memberId;
    private Long parentMealId;
}
