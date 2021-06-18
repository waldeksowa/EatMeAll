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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealDao mealRepository;
    private final MealProductDao mealProductRepository;
    private final StepDao stepRepository;
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
        MealEntity mealEntity = new MealEntity();
        mealEntity.setAuthor(meal.getAuthor());
        mealEntity.setDescription(meal.getDescription());
        mealEntity.setName(meal.getName());
        mealEntity.setMealTime(meal.getMealTime());
        mealEntity.setPrepareTime(meal.getPrepareTime());
//        mealEntity.setProducts(Set.copyOf(products));
//        mealEntity.setSteps(Set.copyOf(steps));
        MealEntity createdMeal = mealRepository.save(mealEntity);

        for (String step : meal.getSteps()) {
            StepEntity aStepEntity = new StepEntity();
            aStepEntity.setDescription(step);
            aStepEntity.setMealId(createdMeal.getId());
            stepRepository.save(aStepEntity);
        }

        List<MealProductEntity> products = meal.getProducts().stream()
                .map(product -> convertToMealProduct(product))
                .collect(Collectors.toList());

        for (MealProductEntity mpe : products) {
            mpe.setMealId(createdMeal.getId());
            mealProductRepository.save(mpe);
        }


        return createdMeal;
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
}
