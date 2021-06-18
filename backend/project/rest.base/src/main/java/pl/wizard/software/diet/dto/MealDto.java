package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;
import pl.wizard.software.diet.meals.MealEntity.MealTimeEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
public class MealDto {
    private Long id;
    private String name;
    private String author;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private MealTimeEnum mealTime;
    private Integer prepareTime;
}
