package pl.wizard.software.diet.dto;

import lombok.Getter;

import java.util.List;
import java.util.Set;

import static pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

@Getter
public class CreateMealDto {
    private String name;
    private String author;
    private String description;
    private Set<MealTimeEnum> mealTime;
    private int prepareTime;
    private List<SimpleProductDto> products;
    private List<String> steps;
}

