package pl.wizard.software.diet.mapper;

import pl.wizard.software.diet.dto.MealDto;
import pl.wizard.software.diet.meals.MealEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MealDtoMapper {

    private MealDtoMapper() {
    }

    public static List<MealDto> mapToMealDtos(List<MealEntity> meals) {
        return meals.stream()
                .map(meal -> mapToMealDto(meal))
                .collect(Collectors.toList());
    }

    public static MealDto mapToMealDto(MealEntity meal) {
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .mealTime(meal.getMealTime())
                .calorific(meal.getCalorific())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .carbohydrates(meal.getCarbohydrates())
                .roughage(meal.getRoughage())
                .build();
    }
}
