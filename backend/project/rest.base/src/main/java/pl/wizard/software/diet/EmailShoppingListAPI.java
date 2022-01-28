package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/emails")
@Slf4j
@RequiredArgsConstructor
public class EmailShoppingListAPI {

    private final LoginService loginService;
    private final EmailShoppingListService emailShoppingListService;

    @PostMapping("/pdf")
    public ResponseEntity sendEmailWithPdf(@RequestHeader("Authorization") String token,
                                    @RequestParam(required = true) String recipient,
                                    @Valid @RequestBody ShoppingListDto shoppingList) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        emailShoppingListService.sendEmailWithPdf(shoppingList, recipient);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/excel")
    public ResponseEntity sendEmailWithExcel(@RequestHeader("Authorization") String token,
                                    @RequestParam(required = true) String recipient,
                                    @Valid @RequestBody ShoppingListDto shoppingList) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        emailShoppingListService.sendEmail();

        return ResponseEntity.ok().build();
    }
}
