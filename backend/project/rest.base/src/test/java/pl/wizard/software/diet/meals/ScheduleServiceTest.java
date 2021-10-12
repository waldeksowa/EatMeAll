package pl.wizard.software.diet.meals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wizard.software.diet.DayDto;
import pl.wizard.software.diet.ScheduleService;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    private ScheduleService scheduleService;

    @Mock
    MealDao mealRepository;
    @Mock
    ScheduleDao scheduleRepository;

    @Before
    public void init() {
        scheduleService = new ScheduleService(mealRepository, scheduleRepository);
    }

    @Test
    public void shouldCalculateMacrosForDayProperly() {
        //given
        ProductEntity firstProduct = new ProductEntity();
        firstProduct.setId(1L);
        firstProduct.setName("first product");
        firstProduct.setCalorific(100.0);
        firstProduct.setCarbohydrates(90.0);
        firstProduct.setFat(80.0);
        firstProduct.setProtein(70.0);
        firstProduct.setRoughage(1.0);

        MealProductEntity firstMealProduct = new MealProductEntity();
        firstMealProduct.setId(1L);
        firstMealProduct.setAmount(100);
        firstMealProduct.setProduct(firstProduct);

        MealEntity meal = new MealEntity();
        meal.setId(1L);
        meal.setName("test meal");
        meal.setProducts(Set.of(firstMealProduct));
        meal.setMealTime(Set.of(MealTimeEnum.BREAKFAST, MealTimeEnum.LUNCH, MealTimeEnum.DINNER, MealTimeEnum.SECOND_BREAKFAST, MealTimeEnum.SUPPER));
        meal.init();

        List<MealEntity> mockList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            mockList.add(meal);
        }
        when(mealRepository.findRandomByMealTime(Mockito.anyInt(),Mockito.anyInt())).thenReturn(mockList);

        //when
        Map<DayOfWeek, DayDto> result = scheduleService.getScheduleByMealTime();

        //then
        assertEquals(500,result.get(DayOfWeek.MONDAY).getCalorific(),0);
        assertEquals(450,result.get(DayOfWeek.MONDAY).getCarbohydrates(),0);
        assertEquals(400,result.get(DayOfWeek.MONDAY).getFat(),0);
        assertEquals(350,result.get(DayOfWeek.MONDAY).getProtein(),0);
        assertEquals(5,result.get(DayOfWeek.MONDAY).getRoughage(),0);
    }

    @Test
    public void shouldCalculateMacrosForDayProperlyWhenHaveOneMealWithTwoOtherProducts() {
        //given
        ProductEntity firstProduct = new ProductEntity();
        firstProduct.setId(1L);
        firstProduct.setName("first product");
        firstProduct.setCalorific(100.0);
        firstProduct.setCarbohydrates(90.0);
        firstProduct.setFat(80.0);
        firstProduct.setProtein(70.0);
        firstProduct.setRoughage(1.0);

        ProductEntity secondProduct = new ProductEntity();
        secondProduct.setId(2L);
        secondProduct.setName("second product");
        secondProduct.setCalorific(101.0);
        secondProduct.setCarbohydrates(91.0);
        secondProduct.setFat(81.0);
        secondProduct.setProtein(71.0);
        secondProduct.setRoughage(2.0);

        MealProductEntity firstMealProduct = new MealProductEntity();
        firstMealProduct.setId(1L);
        firstMealProduct.setAmount(100);
        firstMealProduct.setProduct(firstProduct);

        MealProductEntity secondMealProduct = new MealProductEntity();
        secondMealProduct.setId(2L);
        secondMealProduct.setAmount(100);
        secondMealProduct.setProduct(secondProduct);

        MealEntity meal = new MealEntity();
        meal.setId(1L);
        meal.setName("test meal");
        meal.setProducts(Set.of(firstMealProduct, secondMealProduct));
        meal.setMealTime(Set.of(MealTimeEnum.BREAKFAST, MealTimeEnum.LUNCH, MealTimeEnum.DINNER,MealTimeEnum.SECOND_BREAKFAST, MealTimeEnum.SUPPER));
        meal.init();

        List<MealEntity> mockList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            mockList.add(meal);
        }
        when(mealRepository.findRandomByMealTime(Mockito.anyInt(),Mockito.anyInt())).thenReturn(mockList);

        //when
        Map<DayOfWeek, DayDto> result = scheduleService.getScheduleByMealTime();

        //then
        assertEquals(1005,result.get(DayOfWeek.MONDAY).getCalorific(),0);
        assertEquals(905,result.get(DayOfWeek.MONDAY).getCarbohydrates(),0);
        assertEquals(805,result.get(DayOfWeek.MONDAY).getFat(),0);
        assertEquals(705,result.get(DayOfWeek.MONDAY).getProtein(),0);
        assertEquals(15,result.get(DayOfWeek.MONDAY).getRoughage(),0);

    }

    @Test
    public void shouldCalculateMacrosForDayProperlyWhenHaveFiveOtherMeals() {
        //given
        ProductEntity firstProduct = new ProductEntity();
        firstProduct.setId(1L);
        firstProduct.setName("first product");
        firstProduct.setCalorific(100.0);
        firstProduct.setCarbohydrates(90.0);
        firstProduct.setFat(80.0);
        firstProduct.setProtein(70.0);
        firstProduct.setRoughage(1.0);

        ProductEntity secondProduct = new ProductEntity();
        secondProduct.setId(2L);
        secondProduct.setName("second product");
        secondProduct.setCalorific(101.0);
        secondProduct.setCarbohydrates(91.0);
        secondProduct.setFat(81.0);
        secondProduct.setProtein(71.0);
        secondProduct.setRoughage(2.0);

        ProductEntity thirdProduct = new ProductEntity();
        thirdProduct.setId(3L);
        thirdProduct.setName("third product");
        thirdProduct.setCalorific(102.0);
        thirdProduct.setCarbohydrates(92.0);
        thirdProduct.setFat(82.0);
        thirdProduct.setProtein(72.0);
        thirdProduct.setRoughage(3.0);

        ProductEntity fourthProduct = new ProductEntity();
        fourthProduct.setId(4L);
        fourthProduct.setName("fourth product");
        fourthProduct.setCalorific(103.0);
        fourthProduct.setCarbohydrates(93.0);
        fourthProduct.setFat(83.0);
        fourthProduct.setProtein(73.0);
        fourthProduct.setRoughage(4.0);

        ProductEntity fifthProduct = new ProductEntity();
        fifthProduct.setId(5L);
        fifthProduct.setName("fifth product");
        fifthProduct.setCalorific(104.0);
        fifthProduct.setCarbohydrates(94.0);
        fifthProduct.setFat(84.0);
        fifthProduct.setProtein(74.0);
        fifthProduct.setRoughage(5.0);


        MealProductEntity firstMealProduct = new MealProductEntity();
        firstMealProduct.setId(1L);
        firstMealProduct.setAmount(100);
        firstMealProduct.setProduct(firstProduct);

        MealProductEntity secondMealProduct = new MealProductEntity();
        secondMealProduct.setId(2L);
        secondMealProduct.setAmount(100);
        secondMealProduct.setProduct(secondProduct);

        MealProductEntity thirdMealProduct = new MealProductEntity();
        thirdMealProduct.setId(3L);
        thirdMealProduct.setAmount(100);
        thirdMealProduct.setProduct(thirdProduct);

        MealProductEntity fourthMealProduct = new MealProductEntity();
        fourthMealProduct.setId(4L);
        fourthMealProduct.setAmount(100);
        fourthMealProduct.setProduct(fourthProduct);

        MealProductEntity fifthMealProduct = new MealProductEntity();
        fifthMealProduct.setId(5L);
        fifthMealProduct.setAmount(100);
        fifthMealProduct.setProduct(fifthProduct);

        MealEntity meal_breakfast = new MealEntity();
        meal_breakfast.setId(1L);
        meal_breakfast.setName("test meal BREAKFAST");
        meal_breakfast.setProducts(Set.of(firstMealProduct));
        meal_breakfast.setMealTime(Set.of(MealTimeEnum.BREAKFAST));
        meal_breakfast.init();

        MealEntity meal_second_breakfast = new MealEntity();
        meal_second_breakfast.setId(2L);
        meal_second_breakfast.setName("test meal SECOND_BREAKFAST");
        meal_second_breakfast.setProducts(Set.of(secondMealProduct));
        meal_second_breakfast.setMealTime(Set.of(MealTimeEnum.SECOND_BREAKFAST));
        meal_second_breakfast.init();

        MealEntity meal_lunch = new MealEntity();
        meal_lunch.setId(3L);
        meal_lunch.setName("test meal LUNCH");
        meal_lunch.setProducts(Set.of(thirdMealProduct));
        meal_lunch.setMealTime(Set.of(MealTimeEnum.LUNCH));
        meal_lunch.init();

        MealEntity meal_dinner = new MealEntity();
        meal_dinner.setId(4L);
        meal_dinner.setName("test meal DINNER");
        meal_dinner.setProducts(Set.of(fourthMealProduct));
        meal_dinner.setMealTime(Set.of(MealTimeEnum.DINNER));
        meal_dinner.init();

        MealEntity meal_supper = new MealEntity();
        meal_supper.setId(5L);
        meal_supper.setName("test meal SUPPER");
        meal_supper.setProducts(Set.of(fifthMealProduct));
        meal_supper.setMealTime(Set.of(MealTimeEnum.SUPPER));
        meal_supper.init();

        when(mealRepository.findRandomByMealTime(1, 7)).thenReturn(new ArrayList<>(List.of(meal_breakfast)));
        when(mealRepository.findRandomByMealTime(2, 7)).thenReturn(new ArrayList<>(List.of(meal_second_breakfast)));
        when(mealRepository.findRandomByMealTime(3, 7)).thenReturn(new ArrayList<>(List.of(meal_lunch)));
        when(mealRepository.findRandomByMealTime(4, 7)).thenReturn(new ArrayList<>(List.of(meal_dinner)));
        when(mealRepository.findRandomByMealTime(5, 7)).thenReturn(new ArrayList<>(List.of(meal_supper)));

        //when
        Map<DayOfWeek, DayDto> result = scheduleService.getScheduleByMealTime();

        //then
        assertEquals(510,result.get(DayOfWeek.MONDAY).getCalorific(),0);
        assertEquals(460,result.get(DayOfWeek.MONDAY).getCarbohydrates(),0);
        assertEquals(410,result.get(DayOfWeek.MONDAY).getFat(),0);
        assertEquals(360,result.get(DayOfWeek.MONDAY).getProtein(),0);
        assertEquals(15,result.get(DayOfWeek.MONDAY).getRoughage(),0);

    }
}