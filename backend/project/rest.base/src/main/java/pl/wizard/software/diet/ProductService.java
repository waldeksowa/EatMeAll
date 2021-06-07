package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.diet.products.ProductDao;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productRespository;

    public List<ProductEntity> findAll() {
        return productRespository.findAll();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productRespository.findById(id);
    }

    public ProductEntity save(ProductEntity stock) {
        return productRespository.save(stock);
    }

    public void deleteById(Long id) {
        productRespository.deleteById(id);
    }
}
