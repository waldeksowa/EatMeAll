package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wizard.software.diet.members.MemberEntity;

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
            log.error("Member with id " + id + " is not exists");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }
}
