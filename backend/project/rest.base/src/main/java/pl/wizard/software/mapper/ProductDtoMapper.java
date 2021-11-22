package pl.wizard.software.mapper;

import pl.wizard.software.dto.ProductWithAmountDto;
import pl.wizard.software.diet.meals.MealProductEntity;

public class ProductDtoMapper {

    private ProductDtoMapper() {
    }

    public static ProductWithAmountDto mapToProductWithAmountDto(MealProductEntity mealProduct) {
        String specialAmount = mealProduct.getSpecialAmount() > 0 ?
                    mealProduct.getSpecialAmount() + " " + mealProduct.getSpecialAmountUnit().nameByAmount(mealProduct.getSpecialAmount()) : "";
        return ProductWithAmountDto.builder()
                .id(mealProduct.getProduct().getId())
                .name(mealProduct.getProduct().getName())
                .amount(mealProduct.getAmount())
                .specialAmount(specialAmount)
                .build();
    }

}
