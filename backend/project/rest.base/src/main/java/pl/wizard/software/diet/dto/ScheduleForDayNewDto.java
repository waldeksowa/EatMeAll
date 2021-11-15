package pl.wizard.software.diet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ScheduleForDayNewDto implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long breakfast;
    private Long secondBreakfast;
    private Long lunch;
    private Long dinner;
    private Long supper;
    private double calorific;
    private double carbohydrates;
    private double fat;
    private double protein;
    private double roughage;

//    private void recalculate() {
//        calorific = meals.values().stream().map(meal -> meal.getCalorific()).mapToDouble(Double::doubleValue).sum();
//        carbohydrates = meals.values().stream().map(meal -> meal.getCarbohydrates()).mapToDouble(Double::doubleValue).sum();
//        fat = meals.values().stream().map(meal -> meal.getFat()).mapToDouble(Double::doubleValue).sum();
//        protein = meals.values().stream().map(meal -> meal.getProtein()).mapToDouble(Double::doubleValue).sum();
//        roughage = meals.values().stream().map(meal -> meal.getRoughage()).mapToDouble(Double::doubleValue).sum();
//    }
}
