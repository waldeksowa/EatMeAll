package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MealTimeEnum;
import pl.wizard.software.diet.meals.SpecialAmountEnum;
import pl.wizard.software.dto.CreateMealDto;
import pl.wizard.software.dto.MealDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.mapper.MealDtoMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v2/meals")
@Slf4j
@RequiredArgsConstructor
public class MealAPI {
    private final MealService mealService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<List<MealDto>> findAll(@RequestHeader("Authorization") String token) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(MealDtoMapper.mapToMealDtos(mealService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealEntity> findById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(mealService.findById(id));
    }

    @GetMapping("/random/{amount}/{mealTime}")
    public ResponseEntity<List<MealDto>> findRandomByAmountAndMealTime(
            @RequestHeader("Authorization") String token,
            @PathVariable int amount,
            @PathVariable MealTimeEnum mealTime) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(MealDtoMapper.mapToMealDtos(mealService.findRandomByAmountAndMealTime(amount, mealTime)));
    }

    @GetMapping("/product/specialamount")
    public ResponseEntity<Map<SpecialAmountEnum, String>> getSpecialAmountList(@RequestHeader("Authorization") String token) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(mealService.getSpecialAmountList());
    }

    @GetMapping("/random")
    public ResponseEntity<MealDto> getMealByMealTimeUsingQueryParam(@RequestParam(required = true, name="mealTime")  MealTimeEnum aMealTime) {
        return ResponseEntity.ok(MealDtoMapper.mapToMealDto(mealService.findRandomByMealTime(aMealTime)));
    }


    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateMealDto meal) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.createMeal(meal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealEntity> update(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody MealEntity meal) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(mealService.update(id, meal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        mealService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
