package pl.wizard.software.diet.dto;

import lombok.Getter;
import pl.wizard.software.diet.meals.MealTimeEnum;

import java.util.List;
import java.util.Set;


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

