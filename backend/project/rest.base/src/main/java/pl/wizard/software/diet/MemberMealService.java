package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.meals.*;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.dto.CreateMemberMealDto;
import pl.wizard.software.dto.SimpleProductDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberMealService {
    private final MemberMealDao memberMealRepository;
    private final MealDao mealRepository;
    private final ProductDao productRepository;
    private final MemberDao memberRepository;

    public MemberMealEntity create(CreateMemberMealDto memberMealDto) {
        MemberEntity member = memberRepository.findById(memberMealDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + memberMealDto.getMemberId()));
        MealEntity meal = mealRepository.findById(memberMealDto.getParentMealId())
                .orElseThrow(() -> new NoSuchElementException("Could not find meal with id " + memberMealDto.getParentMealId()));
        List<StepEntity> steps = memberMealDto.getSteps().stream()
                .map(step -> convertToStep(step)).collect(Collectors.toList());
        Set<MealProductEntity> products = memberMealDto.getProducts().stream()
                .map(product -> convertToMealProduct(product)).collect(Collectors.toSet());
        MemberMealEntity memberMeal = new MemberMealEntity();
        memberMeal.setName(memberMealDto.getName());
        memberMeal.setAuthor(memberMealDto.getAuthor());
        memberMeal.setDescription(memberMealDto.getDescription());
        memberMeal.setMealTime(memberMealDto.getMealTime());
        memberMeal.setPrepareTime(memberMealDto.getPrepareTime());
        memberMeal.setProducts(products);
        memberMeal.setSteps(steps);
        memberMeal.setMember(member);
        memberMeal.setParentMeal(meal);

        return memberMealRepository.save(memberMeal);
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
}
