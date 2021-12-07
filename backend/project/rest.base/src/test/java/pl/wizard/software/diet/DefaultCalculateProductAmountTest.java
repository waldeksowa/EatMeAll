package pl.wizard.software.diet;

import org.junit.Before;
import org.junit.Test;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.products.ProductEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class DefaultCalculateProductAmountTest {

    private CalculateProductAmountStrategyIf amountCalculator;

    @Before
    public void init() {
        amountCalculator = new CalculateProductAmountFactory().createCalculator();
    }

    @Test
    public void shouldReturnProductWithAmount500BecauseMemberNeed500CaloriesPerMeal() {
        ProductEntity product = new ProductEntity();
        product.setName("template product");
        product.setCalorific(100D);
        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setProduct(product);
        int templateProductAmount = 1000;
        mealProduct.setAmount(templateProductAmount);
        double templateMealCalories = new BigDecimal((templateProductAmount/100) * product.getCalorific()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double memberCalories = 2500;

        assertEquals(500, amountCalculator.calculateProductAmount(templateProductAmount, 1000, memberCalories));
    }

    @Test
    public void shouldReturnProductWithAmount2000BecauseMemberNeed2000CaloriesPerMeal() {
        ProductEntity product = new ProductEntity();
        product.setName("template product");
        product.setCalorific(100D);
        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setProduct(product);
        int templateProductAmount = 1000;
        mealProduct.setAmount(templateProductAmount);
        double templateMealCalories = new BigDecimal((templateProductAmount/100) * product.getCalorific()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double memberCalories = 10000;

        assertEquals(2000, amountCalculator.calculateProductAmount(templateProductAmount, 1000, memberCalories));
    }

    @Test
    public void shouldReturnProductWithTheSameAmountBecauseProductAmountIsLowerOrEquals10Grams() {
        ProductEntity product = new ProductEntity();
        product.setName("template product");
        product.setCalorific(100D);
        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setProduct(product);
        int templateProductAmount = 10;
        mealProduct.setAmount(templateProductAmount);
        double templateMealCalories = new BigDecimal((templateProductAmount/100) * product.getCalorific()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double memberCalories = 500;

        assertEquals(10, amountCalculator.calculateProductAmount(templateProductAmount, 1000, memberCalories));
    }

}