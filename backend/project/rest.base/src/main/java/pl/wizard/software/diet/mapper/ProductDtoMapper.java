package pl.wizard.software.diet.mapper;

import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.meals.MealProductEntity;

public class ProductDtoMapper {

    private ProductDtoMapper() {
    }

    public static ProductWithAmountDto mapToProductWithAmountDto(MealProductEntity mealProduct) {
        return ProductWithAmountDto.builder()
                .id(mealProduct.getProduct().getId())
                .name(mealProduct.getProduct().getName())
                .amount(mealProduct.getAmount())
                .specialAmount("")
                .build();
    }

}
