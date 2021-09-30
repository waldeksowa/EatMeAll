package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.products.ExcludedProductDao;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcludedProdService {

    private final ExcludedProductDao excludedProductRepository;
    private final ProductDao productRepository;

    public Optional<ExcludedProductEntity> findById(Long productId) {
        return excludedProductRepository.findById(productId);
    }

    public List<ProductEntity> findByMember(Long memberId) {
        return excludedProductRepository.findByMember(memberId);
    }

    public Optional<ProductEntity> findByProductId(Long productId) {
        return productRepository.findById(productId);
    }
}
