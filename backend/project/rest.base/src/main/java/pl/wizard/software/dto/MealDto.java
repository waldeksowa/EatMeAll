package pl.wizard.software.dto;

import lombok.Builder;
import lombok.Getter;
import pl.wizard.software.diet.meals.StepEntity;

import java.util.List;

@Getter
@Builder
public class MealDto {
    private Long id;
    private String name;
    private List<StepEntity> steps;
    private int prepareTime;
    private Double calorific;
    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double roughage;
}
