package pl.wizard.software.diet;

public class DefaultCalculateProductAmount implements CalculateProductAmountIf{
    @Override
    public int calculateProductAmount(int templateProductAmount, double templateMealCalories, double memberMealCalories) {
        return templateProductAmount <= 10 ?
                templateProductAmount :
                (int) Math.round((memberMealCalories/templateMealCalories)*templateProductAmount);
    }
}