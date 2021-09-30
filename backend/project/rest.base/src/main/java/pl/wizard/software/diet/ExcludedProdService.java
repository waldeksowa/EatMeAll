package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.dto.ExcludedProductsDto;
import pl.wizard.software.diet.products.ExcludedProductDao;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.products.ProductDao;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.ArrayList;
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

    public List<Long> findByMember(Long memberId) {
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

    public ExcludedProductsDto create(ExcludedProductsDto excludedProduct) {
        ExcludedProductsDto productDto = ExcludedProductsDto.builder()
                .memberId(excludedProduct.getMemberId())
                .products(new ArrayList<>())
                .build();
        for (Long productId : excludedProduct.getProducts()) {
            if (!productRepository.findById(productId).isPresent()) {
                log.error("Excluded product with id " + productId + " does not exists");
            } else {
                ExcludedProductEntity excludedProductEntity = ExcludedProductEntity.builder()
                        .memberId(excludedProduct.getMemberId())
                        .productId(productId)
                        .build();
                productDto.getProducts().add(excludedProductRepository.save(excludedProductEntity).getProductId());
            }
        }
        return productDto;
    }
}
