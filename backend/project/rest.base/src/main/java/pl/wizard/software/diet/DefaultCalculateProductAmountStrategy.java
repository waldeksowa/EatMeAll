package pl.wizard.software.diet;

public class DefaultCalculateProductAmountStrategy implements CalculateProductAmountStrategyIf {

    public static final int MEALS_PER_DAY = 5;

    @Override
    public int calculateProductAmount(int aTemplateProductAmount, double aTemplateMealCalories, double aMemberCalories) {
        if (aTemplateMealCalories == 0.0) {
            throw new ArithmeticException("TemplateMealCalories should not be zero");
        }
        double oneMealCalorific = aMemberCalories/MEALS_PER_DAY;
        if (aTemplateProductAmount <= 10) {
            return aTemplateProductAmount;
        } else {
            return (int) Math.round((oneMealCalorific/aTemplateMealCalories)*aTemplateProductAmount);
        }
    }
}
