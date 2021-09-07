package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.members.MemberEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/members")
@Slf4j
@RequiredArgsConstructor
public class MemberAPI {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Collection<MemberEntity>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberEntity> findById(@PathVariable Long id) {
        Optional<MemberEntity> stock = memberService.findById(id);
        if (!stock.isPresent()) {
            log.error("Member with id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody MemberEntity member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberEntity> update(@PathVariable Long id, @Valid @RequestBody MemberEntity member) {
        if (!memberService.findById(id).isPresent()) {
            log.error("Member with id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(memberService.save(member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!memberService.findById(id).isPresent()) {
            log.error("Member with id " + id + " does not exists");
            ResponseEntity.badRequest().build();
        }
        memberService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
