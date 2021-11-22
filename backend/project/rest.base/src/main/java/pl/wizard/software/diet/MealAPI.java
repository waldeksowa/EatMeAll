package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.CreateMealDto;
import pl.wizard.software.dto.MealDto;
import pl.wizard.software.mapper.MealDtoMapper;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.meals.SpecialAmountEnum;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/meals")
@Slf4j
@RequiredArgsConstructor
public class MealAPI {
    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealDto>> findAll() {
        return ResponseEntity.ok(MealDtoMapper.mapToMealDtos(mealService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealEntity> findById(@PathVariable Long id) {
        Optional<MealEntity> stock = mealService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @GetMapping("/random/{amount}/{mealTime}")
    public ResponseEntity<List<MealDto>> findRandomByAmountAndMealTime(@PathVariable int amount, @PathVariable MealTimeEnum mealTime) {
        return ResponseEntity.ok(MealDtoMapper.mapToMealDtos(mealService.findRandomByAmountAndMealTime(amount, mealTime)));
    }

    @GetMapping("/product/specialamount")
    public ResponseEntity<Map<SpecialAmountEnum, String>> getSpecialAmountList() {
        return ResponseEntity.ok(mealService.getSpecialAmountList());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CreateMealDto meal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.createMeal(meal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealEntity> update(@PathVariable Long id, @Valid @RequestBody MealEntity meal) {
        if (!mealService.findById(id).isPresent()) {
            log.error("Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mealService.save(meal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!mealService.findById(id).isPresent()) {
            log.error("Id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }
        mealService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
