package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wizard.software.diet.dto.ProductWithAmountDto;
import pl.wizard.software.diet.products.ProductEntity.ProductTypeEnum;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/shoppinglist")
@Slf4j
@RequiredArgsConstructor
public class ShoppingListAPI {

    private final ShoppingListService shoppingListService;

    @GetMapping("/{ids}")
    public ResponseEntity<HashMap<ProductTypeEnum, List<ProductWithAmountDto>>> getShoppingList(@PathVariable List<Long> ids) {
        if (ids.isEmpty()) {
            log.error("List of meals is empty");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(shoppingListService.getShoppingList(ids));
    }
}
