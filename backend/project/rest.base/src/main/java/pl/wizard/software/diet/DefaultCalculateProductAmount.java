package pl.wizard.software.diet;

public class DefaultCalculateProductAmount implements CalculateProductAmountIf{

    public static final int MEALS_PER_DAY = 5;

    @Override
    public int calculateProductAmount(int templateProductAmount, double templateMealCalories, double memberCalories) {
        if (templateMealCalories == 0.0) {
            throw new ArithmeticException("TemplateMealCalories should not be zero");
        }
        double oneMealCalorific = memberCalories/MEALS_PER_DAY;
        if (templateProductAmount <= 10) {
            return templateProductAmount;
        } else {
            return (int) Math.round((oneMealCalorific/templateMealCalories)*templateProductAmount);
        }
    }
}
