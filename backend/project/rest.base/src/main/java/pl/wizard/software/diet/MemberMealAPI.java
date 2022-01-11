package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.CreateMemberMealDto;
import pl.wizard.software.exception.AuthorizationFailedException;
import pl.wizard.software.login.LoginService;
import pl.wizard.software.mapper.MemberMealDtoMapper;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/membermeals")
@Slf4j
@RequiredArgsConstructor
public class MemberMealAPI {
    private final MemberMealService memberMealService;
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateMemberMealDto meal) {
        Long accountId = loginService.getAccountIdByTokenUUID(token)
                .orElseThrow(() -> new AuthorizationFailedException(token));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemberMealDtoMapper.mapToMemberMealDto(memberMealService.create(meal)));
    }
}
