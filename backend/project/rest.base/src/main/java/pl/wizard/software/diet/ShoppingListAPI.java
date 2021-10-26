package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.dto.ShoppingListDto;
import pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum;
import pl.wizard.software.diet.shoppingList.ShoppingListEntity;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/shoppinglist")
@Slf4j
@RequiredArgsConstructor
public class ShoppingListAPI {

    private final LoginService loginService;
    private final ShoppingListService shoppingListService;

    @GetMapping
    public ResponseEntity<HashMap<ProductTypeEnum, List<ProductWithAmountDto>>> getByMemberAndDay(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = true) List<Long> member,
            @RequestParam(required = true) List<DayOfWeek> day) {

        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(shoppingListService.getByMemberAndDay(member, day, accountId.get()));
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<ShoppingListEntity> getById(@RequestHeader("Authorization") String token, @PathVariable Long shoppingListId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        Optional<ShoppingListEntity> shoppingList = shoppingListService.findById(shoppingListId);
        if (!shoppingList.isPresent()) {
            log.error("Shopping list with Id " + shoppingListId + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(shoppingList.get());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody ShoppingListDto shoppingList) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingListService.create(shoppingList));
    }

    @PutMapping("/{shoppingListId}")
    public ResponseEntity<ShoppingListEntity> update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long shoppingListId,
            @Valid @RequestBody ShoppingListEntity shoppingList) {

        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!shoppingListService.findById(shoppingListId).isPresent()) {
            log.error("Shopping list with id " + shoppingListId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(shoppingListService.save(shoppingList));
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long shoppingListId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!shoppingListService.findById(shoppingListId).isPresent()) {
            log.error("Shopping list with id " + shoppingListId + " does not exists");
            return ResponseEntity.badRequest().build();
        }
        shoppingListService.deleteById(shoppingListId);

        return ResponseEntity.ok().build();
    }
}
