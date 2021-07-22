package pl.wizard.software.login;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
public class LoginAPI {

    private final LoginService loginService;

    @PostMapping
    ResponseEntity <Token>login(Account aUser){
        return ResponseEntity.ok(loginService.login(aUser));
    }

}
