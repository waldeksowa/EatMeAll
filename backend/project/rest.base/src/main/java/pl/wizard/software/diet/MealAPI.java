package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.meals.MealEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/meals")
@Slf4j
@RequiredArgsConstructor
public class MealAPI {
    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealEntity>> findAll() {
        return ResponseEntity.ok(mealService.findAll());
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

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody MealEntity meal) {
        return ResponseEntity.ok(mealService.save(meal));
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