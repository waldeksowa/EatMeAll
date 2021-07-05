package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealDto {
    private Long id;
    private String name;
    private Double calorific;
    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double roughage;
}
