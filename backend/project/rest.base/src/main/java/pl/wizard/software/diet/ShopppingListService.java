package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.mapper.ProductDtoMapper;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopppingListService {

    private final MealDao mealRepository;

    HashMap<ProductTypeEnum, List<ProductWithAmountDto>> getShoppingList(List<Long> ids) {
        HashMap<ProductTypeEnum, List<ProductWithAmountDto>> shoppingList = new HashMap<>();
        List<MealProductEntity> mealProducts = new ArrayList<>();
        for (Long id : ids) {
            Optional<MealEntity> meal = mealRepository.findById(id);
            if (!meal.isPresent()) {
                log.error("Meal with id = " + id + " does not exists");
            } else {
                meal.get().getProducts()
                        .forEach(mealProductEntity -> mealProducts.add(mealProductEntity));
            }
        }
        List<MealProductEntity> uniqueProducts = makeProductsUnique(mealProducts);
        prepareShoppingList(shoppingList, uniqueProducts);

        return shoppingList;
    }

    private List<MealProductEntity> makeProductsUnique(List<MealProductEntity> mealProducts) {
        Map<Long, MealProductEntity> mealProductsMap = new HashMap<>();
        for (MealProductEntity mealProduct : mealProducts) {
            MealProductEntity current = mealProductsMap.get(mealProduct.getProduct().getId());
            if (current == null) {
                mealProductsMap.put(mealProduct.getProduct().getId(), mealProduct);
            } else {
                current.setAmount(current.getAmount() + mealProduct.getAmount());
            }
        }
        return List.copyOf(mealProductsMap.values());
    }

    private void prepareShoppingList(HashMap<ProductTypeEnum, List<ProductWithAmountDto>> shoppingList, List<MealProductEntity> uniqueProducts) {
        Set<ProductTypeEnum> types = uniqueProducts.stream()
                .map(mealProductEntity -> mealProductEntity.getProduct().getProductType())
                .collect(Collectors.toSet());
        types.forEach(type -> shoppingList.put(type, new ArrayList<>()));
        for (MealProductEntity uniqueProduct : uniqueProducts) {
            shoppingList.get(uniqueProduct.getProduct().getProductType()).add(ProductDtoMapper.mapToProductWithAmountDto(uniqueProduct));
        }
    }
}
