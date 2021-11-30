package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.products.ProductEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
public class DefaultCalculateProductAmountTest {


    @Test
    public void shouldReturnProductWithAmount500() {
        ProductEntity product = new ProductEntity();
        product.setName("template product");
        product.setCalorific(100D);
        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setProduct(product);
        int templateProductAmount = 1000;
        mealProduct.setAmount(templateProductAmount);
        double templateMealCalories = new BigDecimal((templateProductAmount/100) * product.getCalorific()).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double memberCalories = 500;

        CalculateProductAmountIf calculator = new CalculateProductAmountFactory().createCalculator();

        assertEquals(500, calculator.calculateProductAmount(templateProductAmount, 1000, memberCalories));
    }

}