package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.meals.MealDao;
import pl.wizard.software.diet.meals.MealEntity;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class MealService {
    private final MealDao mealRepository;

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
}
