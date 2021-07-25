package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.products.ProductEntity;
import pl.wizard.software.diet.products.ProductDao;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    Collection<ProductWithTypeDto> findAll() {
        List<ProductEntity.ProductTypeEnum> productTypes = Arrays.stream(ProductEntity.ProductTypeEnum.values()).collect(Collectors.toList());
        HashMap<ProductEntity.ProductTypeEnum, ProductWithTypeDto> hashMap = new HashMap<>();
        productTypes.forEach(pt -> hashMap.put(pt, new ProductWithTypeDto(pt.getProductType())));
        productDao.findAll().forEach(p -> {
            ProductWithTypeDto productType = hashMap.get(p.getProductType());
            productType.addProd(p);
        });
        return hashMap.values();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productDao.findById(id);
    }

    public ProductEntity save(ProductEntity stock) {
        return productDao.save(stock);
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }
}
