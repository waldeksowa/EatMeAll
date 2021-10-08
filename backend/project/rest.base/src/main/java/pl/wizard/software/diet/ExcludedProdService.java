package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.dto.ExcludedProductsDto;
import pl.wizard.software.diet.products.ExcludedProductDao;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ExcludedProductEntity save(ExcludedProductEntity excludedProduct) {
        return excludedProductRepository.save(excludedProduct);
    }

    public void deleteById(Long productId) {
        excludedProductRepository.deleteById(productId);
    }

    @Transactional
    public List<ExcludedProductEntity> create(ExcludedProductsDto excludedProduct) {
        List<Long> actualProducts = findByMember(excludedProduct.getMemberId()).stream()
                .map(prod -> prod.getId())
                .collect(Collectors.toList());
        excludedProduct.getProducts().removeAll(actualProducts);

        List<ExcludedProductEntity> products = new ArrayList<>();
        for (Long productId : excludedProduct.getProducts()) {
            Optional<ProductEntity> product = productRepository.findById(productId);
            if (!product.isPresent()) {
                log.error("Excluded product with id " + productId + " does not exists");
            } else {
                ExcludedProductEntity excludedProductEntity = ExcludedProductEntity.builder()
                        .memberId(excludedProduct.getMemberId())
                        .product(product.get())
                        .build();
                products.add(excludedProductEntity);
            }
        }
        return excludedProductRepository.saveAll(products);
    }
}
