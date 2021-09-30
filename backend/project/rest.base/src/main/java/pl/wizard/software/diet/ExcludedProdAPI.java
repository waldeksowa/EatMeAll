package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.login.LoginService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/excludedprods")
@Slf4j
@RequiredArgsConstructor
public class ExcludedProdAPI {

    private final ExcludedProdService excludedProdService;
    private final ProductService productRepository;
    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        Optional<ExcludedProductEntity> excludedProduct = excludedProdService.findById(id);
        if (!excludedProduct.isPresent()) {
            log.error("Product with id " + id + " does not exists in excluded products");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(excludedProdService.findByProductId(id).get());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ProductEntity>> findByMember(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        Optional<MemberEntity> member = memberService.findById(memberId);
        if (!member.isPresent()) {
            log.error("Member with id " + memberId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(excludedProdService.findByMember(memberId));
    }
//
//    @PostMapping
//    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody MemberEntity member) {
//        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
//        if (!accountId.isPresent()) {
//            log.error("Authorization token expired");
//            return ResponseEntity.badRequest().build();
//        }
//        memberService.setAccountId(member, accountId.get());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(member));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<MemberEntity> update(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody MemberEntity member) {
//        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
//        if (!accountId.isPresent()) {
//            log.error("Authorization token expired");
//            return ResponseEntity.badRequest().build();
//        }
//        if (!memberService.findMemberById(accountId.get(), id).isPresent()) {
//            log.error("Member with id " + id + " does not exists");
//            return ResponseEntity.badRequest().build();
//        }
//
//        return ResponseEntity.ok(memberService.save(member));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
//        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token.substring(7));
//        if (!accountId.isPresent()) {
//            log.error("Authorization token expired");
//            return ResponseEntity.badRequest().build();
//        }
//        if (!memberService.findMemberById(accountId.get(), id).isPresent()) {
//            log.error("Member with id " + id + " does not exists");
//            return ResponseEntity.badRequest().build();
//        }
//        memberService.deleteById(id);
//
//        return ResponseEntity.ok().build();
//    }
}
