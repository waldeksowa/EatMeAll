package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wizard.software.diet.products.HomeProductEntity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/v1/home_products")
@Slf4j
@RequiredArgsConstructor
public class HomeProductAPI {
    private final HomeProductService homeProductService;

    @GetMapping
    public ResponseEntity<Collection<HomeProductWithTypeDto>> findAll() {
        return ResponseEntity.ok(homeProductService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody HomeProductEntity product) {
        return ResponseEntity.ok(homeProductService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomeProductEntity> findById(@PathVariable Long id) {
        Optional<HomeProductEntity> stock = homeProductService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HomeProductEntity> update(@PathVariable Long id, @Valid @RequestBody HomeProductEntity product) {
        if (!homeProductService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(homeProductService.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!homeProductService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        homeProductService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
