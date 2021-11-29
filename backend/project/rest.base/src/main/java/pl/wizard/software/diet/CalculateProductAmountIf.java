package pl.wizard.software.diet;

public interface CalculateProductAmountIf {
    int calculateProductAmount(int templateProductAmount, double templateMealCalories, double memberMealCalories);

    CalculateProductAmountFactory.Type getType();
}
