package pl.wizard.software.mapper;

import pl.wizard.software.dto.MealDto;
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
                .steps(meal.getSteps())
                .prepareTime(meal.getPrepareTime())
                .calorific(meal.getCalorific())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .carbohydrates(meal.getCarbohydrates())
                .roughage(meal.getRoughage())
                .build();
    }
}
