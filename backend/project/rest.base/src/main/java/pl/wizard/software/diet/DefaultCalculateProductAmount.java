package pl.wizard.software.diet;

public class DefaultCalculateProductAmount implements CalculateProductAmountIf{

    public static final int MEALS_PER_DAY = 5;

    @Override
    public int calculateProductAmount(int templateProductAmount, double templateMealCalories, double memberMealCalories) {
        double oneMealCalorific = memberMealCalories/ MEALS_PER_DAY;
        return templateProductAmount <= 10 ?
                templateProductAmount :
                (int) Math.round((oneMealCalorific/templateMealCalories)*templateProductAmount);
    }
}
