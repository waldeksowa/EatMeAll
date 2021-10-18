package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum;
import pl.wizard.software.login.LoginService;

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
//    public ResponseEntity<HashMap<ProductTypeEnum, List<ProductWithAmountDto>>> getCustomShoppingList(
    public ResponseEntity<String> getCustomShoppingList(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = true) Long memberId,
            @RequestParam(required = true) String day) {

        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(memberId.toString() + day.toString());
    }

    @GetMapping("/{ids}")
    public ResponseEntity<HashMap<ProductTypeEnum, List<ProductWithAmountDto>>> getShoppingList(@RequestHeader("Authorization") String token, @PathVariable List<Long> ids) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        if (ids.isEmpty()) {
            log.error("List of meals is empty");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(shoppingListService.getShoppingList(ids));
    }
}
