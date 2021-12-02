package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum;
import pl.wizard.software.diet.shoppingList.ShoppingListEntity;
import pl.wizard.software.dto.CreateShoppingListDto;
import pl.wizard.software.dto.ProductWithAmountDto;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.mapper.ShoppingListDtoMapper;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

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
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(shoppingListService.getByMemberAndDay(member, day, accountId));
    }

    @GetMapping("/current")
    public ResponseEntity<ShoppingListDto> getCurrent(@RequestHeader("Authorization") String token) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        ShoppingListEntity shoppingList = shoppingListService.findCurrent(accountId)
                .orElseThrow(() -> new NoSuchElementException("Could not find current shopping list"));

        return ResponseEntity.ok(ShoppingListDtoMapper.mapToShoppingListDto(shoppingList));
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<ShoppingListDto> getById(@RequestHeader("Authorization") String token, @PathVariable Long shoppingListId) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));
        ShoppingListEntity shoppingList = shoppingListService.findById(accountId, shoppingListId)
                .orElseThrow(() -> new NoSuchElementException("Could not find shopping list with id " + shoppingListId));

        return ResponseEntity.ok(ShoppingListDtoMapper.mapToShoppingListDto(shoppingList));
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateShoppingListDto shoppingList) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ShoppingListDtoMapper.mapToShoppingListDto(shoppingListService.create(accountId, shoppingList)));
    }

    @PutMapping("/{shoppingListId}")
    public ResponseEntity<ShoppingListDto> update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long shoppingListId,
            @Valid @RequestBody ShoppingListDto shoppingList) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.ok(ShoppingListDtoMapper.mapToShoppingListDto(shoppingListService.update(accountId, shoppingListId, shoppingList)));
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long shoppingListId) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        shoppingListService.deleteById(accountId, shoppingListId);
        return ResponseEntity.ok().build();
    }
}
