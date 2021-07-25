package pl.wizard.software.diet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.wizard.software.diet.products.ProductEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Data
public class ProductWithTypeDto {

    private final String type;
    @EqualsAndHashCode.Exclude
    private final List<ProductEntity> products;

    ProductWithTypeDto(String aType) {
        type = aType;
        products = new ArrayList<>();
    }

    void addProd(ProductEntity aProdEntity){
        products.add(aProdEntity);
    }
}
