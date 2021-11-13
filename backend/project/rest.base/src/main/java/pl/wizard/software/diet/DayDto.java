package pl.wizard.software.diet;

import lombok.Getter;
import pl.wizard.software.dto.MealDto;
import pl.wizard.software.diet.meals.MealTimeEnum;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DayDto {

    private double calorific;
    private double carbohydrates;
    private double fat;
    private double protein;
    private double roughage;
    private final Map<MealTimeEnum, MealDto> meals = new HashMap<>();

    void put(MealTimeEnum value, MealDto mapToMealDto) {
        meals.put(value, mapToMealDto);
        recalculate();
    }

    private void recalculate() {
        calorific = meals.values().stream().map(meal -> meal.getCalorific()).mapToDouble(Double::doubleValue).sum();
        carbohydrates = meals.values().stream().map(meal -> meal.getCarbohydrates()).mapToDouble(Double::doubleValue).sum();
        fat = meals.values().stream().map(meal -> meal.getFat()).mapToDouble(Double::doubleValue).sum();
        protein = meals.values().stream().map(meal -> meal.getProtein()).mapToDouble(Double::doubleValue).sum();
        roughage = meals.values().stream().map(meal -> meal.getRoughage()).mapToDouble(Double::doubleValue).sum();
    }
}
