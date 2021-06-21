package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.CreateMealDto;
import pl.wizard.software.diet.dto.SimpleProductDto;
import pl.wizard.software.diet.meals.*;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealDao mealRepository;
    private final ProductDao productRepository;

    public List<MealEntity> findAll() {
        return mealRepository.findAll();
    }

    public Optional<MealEntity> findById(Long id) {
        return mealRepository.findById(id);
    }

    public MealEntity save(MealEntity stock) {
        return mealRepository.save(stock);
    }

    public void deleteById(Long id) {
        mealRepository.deleteById(id);
    }

    public MealEntity createMeal(CreateMealDto meal) {
        Set<StepEntity> steps = meal.getSteps().stream().map(step -> convertToStep(step)).collect(Collectors.toSet());
        Set<MealProductEntity> products = meal.getProducts().stream().map(product -> convertToMealProduct(product)).collect(Collectors.toSet());
        MealEntity mealEntity = new MealEntity(
                meal.getName(),
                meal.getAuthor(),
                meal.getDescription(),
                meal.getMealTime(),
                meal.getPrepareTime(),
                steps,
                products);

        return mealRepository.save(mealEntity);
    }

    private MealProductEntity convertToMealProduct(SimpleProductDto product) {
        Optional<ProductEntity> prod = productRepository.findById(product.getId());
        if (!prod.isPresent()) {
            log.error("Product with Id " + product.getId() + " does not exists");
            return null;
        } else {
            MealProductEntity mealProduct = new MealProductEntity();
            mealProduct.setProduct(prod.get());
            mealProduct.setAmount(product.getAmount());
            return mealProduct;
        }
    }

    private StepEntity convertToStep(String step) {
        StepEntity stepEntity = new StepEntity();
        stepEntity.setDescription(step);
        return stepEntity;
    }
}
