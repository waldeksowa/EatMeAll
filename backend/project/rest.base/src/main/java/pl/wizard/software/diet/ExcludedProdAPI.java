package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.dto.ExcludedProductsDto;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.login.LoginService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/excludedprods")
@Slf4j
@RequiredArgsConstructor
public class ExcludedProdAPI {

    private final ExcludedProdService excludedProdService;
    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> findById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
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
    public ResponseEntity<List<Long>> findByMember(@RequestHeader("Authorization") String token, @PathVariable Long memberId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
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

    @PostMapping
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid @RequestBody ExcludedProductsDto excludedProduct) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!memberService.findMemberById(accountId.get(), excludedProduct.getMemberId()).isPresent()) {
            log.error("Member with id " + excludedProduct.getMemberId() + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(excludedProdService.create(excludedProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ExcludedProductEntity> update(@RequestHeader("Authorization") String token, @PathVariable Long productId, @Valid @RequestBody ExcludedProductEntity excludedProduct) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!memberService.findMemberById(accountId.get(), excludedProduct.getMemberId()).isPresent()) {
            log.error("Member with id " + excludedProduct.getMemberId() + " does not exists");
            return ResponseEntity.badRequest().build();
        }
        if (!excludedProdService.findById(productId).isPresent()) {
            log.error("Excluded product with id " + productId + " does not exists");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(excludedProdService.save(excludedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        Optional<Long> accountId = loginService.getAccountIdByTokenUUID(token);
        if (!accountId.isPresent()) {
            log.error("Authorization token expired");
            return ResponseEntity.badRequest().build();
        }
        if (!excludedProdService.findById(productId).isPresent()) {
            log.error("Product with id " + productId + " does not exists");
            return ResponseEntity.badRequest().build();
        }
        excludedProdService.deleteById(productId);

        return ResponseEntity.ok().build();
    }
}
