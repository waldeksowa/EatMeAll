package pl.wizard.software.diet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealProductEntity;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {

    private MealService mealService;

    @Mock
    CalculateProductAmountFactory calculateProductAmountFactory;
    @Mock
    MealDao mealRepository;
    @Mock
    ProductDao productRepository;
    @Mock
    MemberDao memberRepository;

    @Before
    public void init() {
        mealService = new MealService(mealRepository, productRepository, calculateProductAmountFactory, memberRepository);
    }

    @Test
    public void shouldReturnMealWithHalfCalorificBecauseMemberNeeds500CaloriesPerMeal() {
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("test product");
        product.setCalorific(100D);
        product.setProtein(100D);
        product.setFat(100D);
        product.setCarbohydrates(100D);
        product.setRoughage(100D);

        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setId(10L);
        mealProduct.setAmount(1000);
        mealProduct.setProduct(product);

        MealEntity meal = new MealEntity();
        meal.setId(100L);
        meal.setName("test meal");
        meal.setProducts(Set.of(mealProduct));
        meal.setCalorific(1000D);

        MemberEntity member = new MemberEntity();
        member.setId(2L);
        member.setName("test member");
        member.setRecommendedCalories(2500D);

        Mockito.lenient().when(mealRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(meal));
        Mockito.lenient().when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Mockito.lenient().when(calculateProductAmountFactory.createCalculator()).thenReturn(new DefaultCalculateProductAmount());

        MealEntity result = mealService.findByIdAndMember(100L, 2L);

        assertEquals(500D, result.getCalorific(), 0.01);
    }

    @Test
    public void shouldReturnMealWithTwiceCalorificBecauseMemberNeeds2000CaloriesPerMeal() {
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("test product");
        product.setCalorific(100D);
        product.setProtein(100D);
        product.setFat(100D);
        product.setCarbohydrates(100D);
        product.setRoughage(100D);

        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setId(10L);
        mealProduct.setAmount(1000);
        mealProduct.setProduct(product);

        MealEntity meal = new MealEntity();
        meal.setId(100L);
        meal.setName("test meal");
        meal.setProducts(Set.of(mealProduct));
        meal.setCalorific(1000D);

        MemberEntity member = new MemberEntity();
        member.setId(2L);
        member.setName("test member");
        member.setRecommendedCalories(10000D);

        Mockito.lenient().when(mealRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(meal));
        Mockito.lenient().when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Mockito.lenient().when(calculateProductAmountFactory.createCalculator()).thenReturn(new DefaultCalculateProductAmount());

        MealEntity result = mealService.findByIdAndMember(100L, 2L);

        assertEquals(2000D, result.getCalorific(), 0.01);
    }

    @Test
    public void shouldReturnMealWithTheSameCalorificBecauseProductAmountIsEquals10Grams() {
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("test product");
        product.setCalorific(10000D);
        product.setProtein(100D);
        product.setFat(100D);
        product.setCarbohydrates(100D);
        product.setRoughage(100D);

        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setId(10L);
        mealProduct.setAmount(10);
        mealProduct.setProduct(product);

        MealEntity meal = new MealEntity();
        meal.setId(100L);
        meal.setName("test meal");
        meal.setProducts(Set.of(mealProduct));
        meal.setCalorific(1000D);

        MemberEntity member = new MemberEntity();
        member.setId(2L);
        member.setName("test member");
        member.setRecommendedCalories(5000D);

        Mockito.lenient().when(mealRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(meal));
        Mockito.lenient().when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Mockito.lenient().when(calculateProductAmountFactory.createCalculator()).thenReturn(new DefaultCalculateProductAmount());

        MealEntity result = mealService.findByIdAndMember(100L, 2L);

        assertEquals(1000D, result.getCalorific(), 0.01);
    }
}