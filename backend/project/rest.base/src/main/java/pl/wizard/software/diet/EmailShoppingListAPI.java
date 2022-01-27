package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wizard.software.login.LoginService;

import java.util.Optional;

@RestController
@RequestMapping("/v2/emails")
@Slf4j
@RequiredArgsConstructor
public class EmailShoppingListAPI {

    private final LoginService loginService;
    private final EmailShoppingListService emailShoppingListService;

    @GetMapping
    public ResponseEntity sendEmail(@RequestHeader("Authorization") String token) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        emailShoppingListService.sendEmail();

        return ResponseEntity.ok().build();
    }
}
