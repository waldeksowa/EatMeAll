package pl.wizard.software.diet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wizard.software.diet.meals.MealDao;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ShopppingListServiceTest {

    @Mock
    MealDao mealRepository;

    @Test
    public void shouldReturnZero() {
        assertEquals(0, 0);
    }

}