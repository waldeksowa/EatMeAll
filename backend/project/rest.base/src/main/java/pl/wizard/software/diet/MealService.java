package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.meals.*;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.dto.CreateMealDto;
import pl.wizard.software.dto.SimpleProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealDao mealRepository;
    private final ProductDao productRepository;
    private final CalculateProductAmountFactory calculateProductAmountFactory;
    private final MemberDao memberRepository;

    public List<MealEntity> findAll() {
        return mealRepository.findAll();
    }

    public Optional<MealEntity> findById(Long id) {
        return mealRepository.findById(id);
    }

    public MealEntity findByIdAndMember(Long mealId, Long memberId) {
        MealEntity meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new NoSuchElementException("Could not find meal with id " + mealId));
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + memberId));

        return customizeByCalories(member.getRecommendedCalories(), meal);
    }

    @Transactional
    public MealEntity save(Long mealId, MealEntity meal) {
        MealEntity mealToUpdate = mealRepository.findById(mealId)
                .orElseThrow(() -> new NoSuchElementException("Could not find meal with id " + mealId));
        mealToUpdate.setName(meal.getName());
        mealToUpdate.setAuthor(meal.getAuthor());
        mealToUpdate.setMealTime(meal.getMealTime());
        mealToUpdate.setPrepareTime(meal.getPrepareTime());
        mealToUpdate.setSteps(meal.getSteps());
        mealToUpdate.setDescription(meal.getDescription());
        mealToUpdate.setProducts(meal.getProducts());
        return mealToUpdate;
    }

    @Transactional
    public void deleteById(Long id) {
        MealEntity mealToUpdate = mealRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find meal with id " + id));
        mealRepository.deleteById(id);
    }

    public MealEntity createMeal(CreateMealDto meal) {
        List<StepEntity> steps = meal.getSteps().stream().map(step -> convertToStep(step)).collect(Collectors.toList());
        Set<MealProductEntity> products = meal.getProducts().stream().map(product -> convertToMealProduct(product)).collect(Collectors.toSet());
        MealEntity mealEntity = MealEntity.builder()
                .name(meal.getName())
                .author(meal.getAuthor())
                .description(meal.getDescription())
                .mealTime(meal.getMealTime())
                .prepareTime(meal.getPrepareTime())
                .steps(steps)
                .products(products)
                .build();

        return mealRepository.save(mealEntity);
    }

    private MealProductEntity convertToMealProduct(SimpleProductDto product) {
        ProductEntity prod = productRepository.findById(product.getId())
                .orElseThrow(() -> new NoSuchElementException("Could not find product with id " + product.getId()));
        MealProductEntity mealProduct = new MealProductEntity();
        mealProduct.setProduct(prod);
        mealProduct.setAmount(product.getAmount());
        mealProduct.setSpecialAmount(product.getSpecialAmount());
        mealProduct.setSpecialAmountUnit(product.getSpecialAmountUnit());
        return mealProduct;
    }

    private StepEntity convertToStep(String step) {
        StepEntity stepEntity = new StepEntity();
        stepEntity.setDescription(step);
        return stepEntity;
    }

    public List<MealEntity> findRandomByAmountAndMealTime(int amount, MealTimeEnum mealTime) {
        return mealRepository.findRandomByMealTime(mealTime.ordinal(), amount);
    }

    public Map<SpecialAmountEnum, String> getSpecialAmountList() {
        Map<SpecialAmountEnum, String> specialAmountList = new HashMap<>();
        for (SpecialAmountEnum specialamount : SpecialAmountEnum.values()) {
            specialAmountList.put(specialamount, specialamount.nameByAmount(1));
        }
        return specialAmountList;
    }

    public MealEntity customizeByCalories(double memberCalories, MealEntity mealEntity) {
        CalculateProductAmountIf amountCalculator = calculateProductAmountFactory.createCalculator();
        mealEntity.getProducts().forEach(
                mealProduct -> mealProduct.setAmount(amountCalculator.calculateProductAmount(mealProduct.getAmount(), mealEntity.getCalorific(), memberCalories))
        );
        recalculateMealMacros(mealEntity);
        return mealEntity;
    }

    private void recalculateMealMacros(MealEntity mealEntity) {
        double calorific = mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getCalorific()).sum();
        double protein = mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getProtein()).sum();
        double fat = mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getFat()).sum();
        double carbohydrates = mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getCarbohydrates()).sum();
        double roughage = mealEntity.getProducts().stream()
                .mapToDouble(mealProduct -> (mealProduct.getAmount() / 100.0) * mealProduct.getProduct().getRoughage()).sum();

        mealEntity.setCalorific(new BigDecimal(calorific).setScale(1, RoundingMode.HALF_UP).doubleValue());
        mealEntity.setProtein(new BigDecimal(protein).setScale(1, RoundingMode.HALF_UP).doubleValue());
        mealEntity.setFat(new BigDecimal(fat).setScale(1, RoundingMode.HALF_UP).doubleValue());
        mealEntity.setCarbohydrates(new BigDecimal(carbohydrates).setScale(1, RoundingMode.HALF_UP).doubleValue());
        mealEntity.setRoughage(new BigDecimal(roughage).setScale(1, RoundingMode.HALF_UP).doubleValue());
    }
}
