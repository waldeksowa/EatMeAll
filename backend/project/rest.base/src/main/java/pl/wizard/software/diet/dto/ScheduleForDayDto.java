package pl.wizard.software.diet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;

import java.io.Serializable;
import java.time.LocalDate;

import static pl.wizard.software.diet.meals.MealTimeEnum.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForDayDto implements Serializable {
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

    public void add(MealEntity mealEntity, MealTimeEnum mealTime) {
        if (mealTime == BREAKFAST) {
            breakfast = mealEntity.getId();
        } else if (mealTime == SECOND_BREAKFAST) {
            secondBreakfast = mealEntity.getId();
        } else if (mealTime == LUNCH) {
            lunch = mealEntity.getId();
        } else if (mealTime == DINNER) {
            dinner = mealEntity.getId();
        } else if (mealTime == SUPPER) {
            supper = mealEntity.getId();
        }
        recalculate(mealEntity);
    }

    private void recalculate(MealEntity mealEntity) {
        calorific += mealEntity.getCalorific();
        carbohydrates += mealEntity.getCarbohydrates();
        fat += mealEntity.getFat();
        protein += mealEntity.getProtein();
        roughage += mealEntity.getRoughage();
    }
}
