package pl.wizard.software.diet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;
import pl.wizard.software.diet.shoppingList.ShoppingListDao;
import pl.wizard.software.diet.shoppingList.ShoppingListItemDao;
import pl.wizard.software.dto.ProductWithAmountDto;
import pl.wizard.software.dto.ScheduleForDayDto;
import pl.wizard.software.login.AccountDao;
import pl.wizard.software.mapper.ScheduleDtoMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum.CEREALS;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class ShoppingListServiceTest {

    private ShoppingListService shoppingListService;

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
    MealDao mealRepository;
    @Mock
    CalculateProductAmountFactory calculateProductAmountFactory;
    @Mock
    ScheduleService scheduleService;

    @Before
    public void init() {
        shoppingListService = new ShoppingListService(new MealService(mealRepository, productRepository, calculateProductAmountFactory, memberRepository),
                                                        shoppingListRepository,
                                                        shoppingListItemRepository,
                                                        new ScheduleService(new MealService(mealRepository, productRepository, calculateProductAmountFactory, memberRepository), scheduleRepository,memberRepository), productRepository, accountRepository);
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

    @Test
    public void shouldReturnShoppingListWithTwoProductsAndHalfAmountOfEachProductWhenHaveTwoTheSameMeals() {
        ProductEntity firstProduct = new ProductEntity();
        firstProduct.setId(1L);
        firstProduct.setName("first product");
        firstProduct.setProductType(CEREALS);
        firstProduct.setCalorific(100D);
        firstProduct.setProtein(100D);
        firstProduct.setFat(100D);
        firstProduct.setCarbohydrates(100D);
        firstProduct.setRoughage(100D);

        ProductEntity secondProduct = new ProductEntity();
        secondProduct.setId(2L);
        secondProduct.setName("second product");
        secondProduct.setProductType(CEREALS);
        secondProduct.setCalorific(100D);
        secondProduct.setProtein(100D);
        secondProduct.setFat(100D);
        secondProduct.setCarbohydrates(100D);
        secondProduct.setRoughage(100D);

        MealProductEntity firstMealProduct = new MealProductEntity();
        firstMealProduct.setId(10L);
        firstMealProduct.setAmount(1000);
        firstMealProduct.setProduct(firstProduct);

        MealProductEntity secondMealProduct = new MealProductEntity();
        secondMealProduct.setId(20L);
        secondMealProduct.setAmount(1000);
        secondMealProduct.setProduct(secondProduct);

        MealEntity meal = new MealEntity();
        meal.setId(100L);
        meal.setName("test meal");
        meal.setProducts(Set.of(firstMealProduct, secondMealProduct));
        meal.setCalorific(1000D);

        MemberEntity member = new MemberEntity();
        member.setId(2L);
        member.setName("test member");
        member.setRecommendedCalories(2500D);

        ScheduleForDayDto scheduleForDay = new ScheduleForDayDto();
        scheduleForDay.setDate(LocalDate.of(2021, 12, 6));
        scheduleForDay.setBreakfast(100L);
        scheduleForDay.setSecondBreakfast(100L);
        scheduleForDay.setLunch(100L);
        scheduleForDay.setDinner(100L);
        scheduleForDay.setSupper(100L);

        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(5L);
        scheduleEntity.setMemberId(2L);
        scheduleEntity.setScheduleDate(LocalDate.of(2021, 12, 6));
        scheduleEntity.setSchedule(ScheduleDtoMapper.convertToBytes(List.of(scheduleForDay)));

        Mockito.lenient().when(mealRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(meal));
        Mockito.lenient().when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Mockito.lenient().when(calculateProductAmountFactory.createCalculator()).thenReturn(new DefaultCalculateProductAmount());
        Mockito.lenient().when(scheduleService.findByMember(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(scheduleEntity));
        Mockito.lenient().when(scheduleRepository.findByMember(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(List.of(scheduleEntity));

        HashMap<ProductEntity.ProductTypeEnum, List<ProductWithAmountDto>> result = shoppingListService.getByMemberAndDay(List.of(2L), List.of(DayOfWeek.MONDAY), 1L);

        assertEquals(1, result.keySet().size());
        assertTrue(result.keySet().stream().anyMatch(key -> key == CEREALS));
        assertEquals(2, result.get(CEREALS).size());
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(product -> product.getName().equals("first product") && product.getAmount() == 1250));
        assertTrue(result.get(CEREALS).stream()
                .anyMatch(product -> product.getName().equals("second product") && product.getAmount() == 1250));
    }

    @Test
    public void shouldReturnShoppingListWithOneProductAndHalfAmountWhenHaveTwoMealsWithTheSameProduct() {
        ProductEntity testProduct = new ProductEntity();
        testProduct.setId(1L);
        testProduct.setName("test product");
        testProduct.setProductType(CEREALS);
        testProduct.setCalorific(100D);
        testProduct.setProtein(100D);
        testProduct.setFat(100D);
        testProduct.setCarbohydrates(100D);
        testProduct.setRoughage(100D);

        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setId(10L);
        mealProduct.setAmount(1000);
        mealProduct.setProduct(testProduct);

        MealEntity firstMeal = new MealEntity();
        firstMeal.setId(100L);
        firstMeal.setName("test meal");
        firstMeal.setProducts(Set.of(mealProduct));
        firstMeal.setCalorific(1000D);

        MealEntity secondMeal = new MealEntity();
        secondMeal.setId(200L);
        secondMeal.setName("test meal");
        secondMeal.setProducts(Set.of(mealProduct));
        secondMeal.setCalorific(1000D);

        MemberEntity member = new MemberEntity();
        member.setId(2L);
        member.setName("test member");
        member.setRecommendedCalories(2500D);

        ScheduleForDayDto scheduleForDay = new ScheduleForDayDto();
        scheduleForDay.setDate(LocalDate.of(2021, 12, 6));
        scheduleForDay.setBreakfast(100L);
        scheduleForDay.setSecondBreakfast(200L);
        scheduleForDay.setLunch(100L);
        scheduleForDay.setDinner(200L);
        scheduleForDay.setSupper(100L);

        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setId(5L);
        scheduleEntity.setMemberId(2L);
        scheduleEntity.setScheduleDate(LocalDate.of(2021, 12, 6));
        scheduleEntity.setSchedule(ScheduleDtoMapper.convertToBytes(List.of(scheduleForDay)));

        Mockito.lenient().when(mealRepository.findById(100L)).thenReturn(Optional.of(firstMeal));
        Mockito.lenient().when(mealRepository.findById(200L)).thenReturn(Optional.of(secondMeal));
        Mockito.lenient().when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Mockito.lenient().when(calculateProductAmountFactory.createCalculator()).thenReturn(new DefaultCalculateProductAmount());
        Mockito.lenient().when(scheduleService.findByMember(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(scheduleEntity));
        Mockito.lenient().when(scheduleRepository.findByMember(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(List.of(scheduleEntity));

        HashMap<ProductEntity.ProductTypeEnum, List<ProductWithAmountDto>> result = shoppingListService.getByMemberAndDay(List.of(2L), List.of(DayOfWeek.MONDAY), 1L);

        assertEquals(1, result.keySet().size());
        assertTrue(result.keySet().stream().anyMatch(key -> key == CEREALS));
        assertEquals(1, result.get(CEREALS).size());
//        assertTrue(result.get(CEREALS).stream()
//                .anyMatch(product -> product.getName().equals("test product") && product.getAmount() == 1250));
    }

}