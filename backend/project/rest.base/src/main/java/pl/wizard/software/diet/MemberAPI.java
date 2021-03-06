package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.dto.MemberDto;
import pl.wizard.software.dto.MemberWithoutScheduleAndProdsDto;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v2/members")
@Slf4j
@RequiredArgsConstructor
public class MemberAPI {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Collection<MemberWithoutScheduleAndProdsDto>> findAll(@RequestHeader("Authorization") String token) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(memberService.findAllMembers(accountId.get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        Optional<MemberDto> member = memberService.findMemberById(accountId.get(), id);
        if (!member.isPresent()) {
            log.error("Member with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(member.get());
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody MemberEntity member) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(member, accountId.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberEntity> update(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody MemberEntity member) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!memberService.findMemberById(accountId.get(), id).isPresent()) {
            log.error("Member with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(memberService.save(member, accountId.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!memberService.findMemberById(accountId.get(), id).isPresent()) {
            log.error("Member with id " + id + " does not exists");
            return ResponseEntity.badRequest().build();
        }
        memberService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
