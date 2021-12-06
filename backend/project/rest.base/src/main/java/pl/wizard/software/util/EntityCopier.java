package pl.wizard.software.util;

import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;

import java.util.List;
import java.util.Set;

public class EntityCopier {
    public static MealEntity copyOf(MealEntity mealEntity) {
        MealEntity result = new MealEntity();
        result.setId(mealEntity.getId());
        result.setCreatedAt(mealEntity.getCreatedAt());
        result.setUpdatedAt(mealEntity.getUpdatedAt());
        result.setVersion(mealEntity.getVersion());
        result.setName(mealEntity.getName());
        result.setAuthor(mealEntity.getAuthor());
        result.setDescription(mealEntity.getDescription());
        result.setPrepareTime(mealEntity.getPrepareTime());
        if (mealEntity.getMealTime() != null) {
            result.setMealTime(mealEntity.getMealTime());
        }
        if (mealEntity.getProducts() != null) {
            result.setProducts(Set.copyOf(mealEntity.getProducts()));
        }
        if (mealEntity.getSteps() != null) {
            result.setSteps(List.copyOf(mealEntity.getSteps()));
        }
        result.setCalorific(mealEntity.getCalorific());
        result.setProtein(mealEntity.getProtein());
        result.setFat(mealEntity.getFat());
        result.setCarbohydrates(mealEntity.getCarbohydrates());
        result.setRoughage(mealEntity.getRoughage());
        return result;
    }

    public static MealProductEntity copyOf(MealProductEntity mealProductEntity) {
        MealProductEntity result = new MealProductEntity();
        result.setId(mealProductEntity.getId());
        result.setCreatedAt(mealProductEntity.getCreatedAt());
        result.setUpdatedAt(mealProductEntity.getUpdatedAt());
        result.setVersion(mealProductEntity.getVersion());
        result.setProduct(mealProductEntity.getProduct());
        result.setAmount(mealProductEntity.getAmount());
        result.setSpecialAmount(mealProductEntity.getSpecialAmount());
        result.setSpecialAmountUnit(mealProductEntity.getSpecialAmountUnit());
        return result;
    }
}
