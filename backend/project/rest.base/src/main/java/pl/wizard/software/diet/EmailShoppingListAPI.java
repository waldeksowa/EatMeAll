package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.email.NewEmailSender;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/shoppinglistemails")
@Slf4j
@RequiredArgsConstructor
public class EmailShoppingListAPI {

    private final LoginService loginService;
    private final EmailShoppingListService emailShoppingListService;
    private final NewEmailSender newEmailSender;


    @GetMapping
    public void testMail() {
        newEmailSender.sendEmail("waldek@szkola-dobrego-kodu.pl", "test mail", "test message");
    }

    @PostMapping("/pdf")
    public ResponseEntity sendEmailWithAttachedPdf(@RequestHeader("Authorization") String token,
                                    @RequestParam(required = true) String recipient,
                                    @Valid @RequestBody ShoppingListDto shoppingList) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        emailShoppingListService.sendEmailWithPdf(shoppingList, recipient);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/excel")
    public ResponseEntity sendEmailWithAttachedExcel(@RequestHeader("Authorization") String token,
                                    @RequestParam(required = true) String recipient,
                                    @Valid @RequestBody ShoppingListDto shoppingList) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        emailShoppingListService.sendEmailWithExcel(shoppingList, recipient);
        return ResponseEntity.ok().build();
    }
}
