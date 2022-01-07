package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.meals.MealEntity;
import pl.wizard.software.diet.meals.MemberMealEntity;
import pl.wizard.software.dto.CreateMemberMealDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v2/membermeals")
@Slf4j
@RequiredArgsConstructor
public class MemberMealAPI {
    private final MemberMealService memberMealService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<List<MemberMealEntity>> findAll(@RequestHeader("Authorization") String token) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(memberMealService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberMealEntity> findById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(memberMealService.findById(id));
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateMemberMealDto meal) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(memberMealService.create(meal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealEntity> update(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody MemberMealEntity meal) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(memberMealService.update(id, meal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        memberMealService.delete(id);
        return ResponseEntity.ok().build();
    }

}
