package pl.wizard.software.mapper;

import pl.wizard.software.diet.meals.MemberMealEntity;
import pl.wizard.software.dto.MemberMealDto;

public class MemberMealDtoMapper {

    private MemberMealDtoMapper() {
    }

    public static MemberMealDto mapToMemberMealDto(MemberMealEntity memberMeal) {
        return MemberMealDto.builder()
                .id(memberMeal.getId())
                .name(memberMeal.getName())
                .author(memberMeal.getAuthor())
                .description(memberMeal.getDescription())
                .mealTime(memberMeal.getMealTime())
                .prepareTime(memberMeal.getPrepareTime())
                .products(memberMeal.getProducts())
                .steps(memberMeal.getSteps())
                .calorific(memberMeal.getCalorific())
                .protein(memberMeal.getProtein())
                .fat(memberMeal.getFat())
                .carbohydrates(memberMeal.getCarbohydrates())
                .roughage(memberMeal.getRoughage())
                .memberId(memberMeal.getMember().getId())
                .parentMealId(memberMeal.getParentMeal().getId())
                .build();
    }
}
