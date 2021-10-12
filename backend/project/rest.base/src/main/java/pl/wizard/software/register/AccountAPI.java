package pl.wizard.software.register;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wizard.software.register.dto.AccountDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/register")
@Slf4j
@RequiredArgsConstructor
public class AccountAPI {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody AccountDto aAccountDto){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
