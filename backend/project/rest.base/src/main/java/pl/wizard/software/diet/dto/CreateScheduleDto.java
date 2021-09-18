package pl.wizard.software.diet.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateScheduleDto {
    private LocalDate mealDate;
    private List<MealWithTimeDto> meals;
}
