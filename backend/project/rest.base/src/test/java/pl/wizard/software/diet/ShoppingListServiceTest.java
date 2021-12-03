package pl.wizard.software.diet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.dto.ProductWithAmountDto;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.shoppingList.ShoppingListDao;
import pl.wizard.software.diet.shoppingList.ShoppingListItemDao;
import pl.wizard.software.login.AccountDao;

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
    @Mock
    ShoppingListDao shoppingListRepository;
    @Mock
    ScheduleDao scheduleRepository;
    @Mock
    ProductDao productRepository;
    @Mock
    AccountDao accountRepository;
    @Mock
    ShoppingListItemDao shoppingListItemRepository;
    @Mock
    MemberDao memberRepository;
    @Mock
    MealService mealService;

    @Before
    public void init() {
        shoppingListService = new ShoppingListService(mealService, shoppingListRepository, shoppingListItemRepository, new ScheduleService(mealService, scheduleRepository,memberRepository), productRepository, accountRepository);
    }

    @Test
    public void shouldReturnShoppingListWithTwoProductsWhenHaveTwoTheSameMeals() {
        //given
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
        //when
        HashMap<ProductEntity.ProductTypeEnum, List<ProductWithAmountDto>> result = shoppingListService.getShoppingList(List.of(1L, 2L));
        //then
        assertEquals(1, result.keySet().size());
        assertTrue(result.keySet().stream().anyMatch(key -> key == CEREALS));
        assertEquals(2, result.get(CEREALS).size());
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(product -> product.getName().equals("first product") && product.getAmount() == 20));
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(product -> product.getName().equals("second product") && product.getAmount() == 40));
    }

    @Test
    public void shouldReturnShoppingListWithOneProductWhenHaveTwoMealsWithTheSameProduct() {
        //given
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("test product");
        product.setProductType(CEREALS);

        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setId(100L);
        mealProduct.setAmount(100);
        mealProduct.setProduct(product);

        MealEntity firstMeal = new MealEntity();
        firstMeal.setId(1L);
        firstMeal.setName("first meal");
        firstMeal.setProducts(Set.of(mealProduct));
        MealEntity secondMeal = new MealEntity();
        secondMeal.setId(2L);
        secondMeal.setName("second meal");
        secondMeal.setProducts(Set.of(mealProduct));

        Mockito.lenient().when(mealRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(firstMeal));
        Mockito.lenient().when(mealRepository.findById(Mockito.eq(2L))).thenReturn(Optional.of(secondMeal));
        //when
        HashMap<ProductEntity.ProductTypeEnum, List<ProductWithAmountDto>> result = shoppingListService.getShoppingList(List.of(1L, 2L));
        //then
        assertEquals(1, result.keySet().size());
        assertTrue(result.keySet().stream().anyMatch(key -> key == CEREALS));
        assertEquals(1, result.get(CEREALS).size());
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(prod -> prod.getName().equals("test product") && prod.getAmount() == 200));
    }

}