package pl.wizard.software.diet;

public interface CalculateProductAmountStrategyIf {
    int calculateProductAmount(int templateProductAmount, double templateMealCalories, double memberMealCalories);
}
