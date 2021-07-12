package pl.wizard.software.diet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum.CEREALS;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    private ShoppingListService shoppingListService;

    @Mock
    MealDao mealRepository;

    @Before
    public void init() {
        shoppingListService = new ShoppingListService(mealRepository);

        ProductEntity firstProduct = new ProductEntity();
        firstProduct.setId(1L);
        firstProduct.setName("first product");
        firstProduct.setProductType(CEREALS);
        ProductEntity secondProduct = new ProductEntity();
        secondProduct.setId(2L);
        secondProduct.setName("second product");
        secondProduct.setProductType(CEREALS);

        MealProductEntity firstMealProduct = new MealProductEntity();
        firstMealProduct.setId(10L);
        firstMealProduct.setAmount(10);
        firstMealProduct.setProduct(firstProduct);
        MealProductEntity secondMealProduct = new MealProductEntity();
        secondMealProduct.setId(20L);
        secondMealProduct.setAmount(20);
        secondMealProduct.setProduct(secondProduct);

        MealEntity meal = new MealEntity();
        meal.setId(1L);
        meal.setName("test meal");
        meal.setProducts(Set.of(firstMealProduct, secondMealProduct));

        Mockito.lenient().when(mealRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(meal));
    }

    @Test
    public void shouldReturnShoppingListWithTwoProducts() {
        HashMap<ProductEntity.ProductTypeEnum, List<ProductWithAmountDto>> result = shoppingListService.getShoppingList(List.of(1L, 2L));

        assertEquals(1, result.keySet().size());
        assertTrue(result.keySet().stream().anyMatch(key -> key == CEREALS));
        assertEquals(2, result.get(CEREALS).size());
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(product -> product.getName().equals("first product") && product.getAmount() == 20));
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(product -> product.getName().equals("second product") && product.getAmount() == 40));
    }

}