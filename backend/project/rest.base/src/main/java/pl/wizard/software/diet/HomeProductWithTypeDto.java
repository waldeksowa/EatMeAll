package pl.wizard.software.diet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.wizard.software.diet.products.HomeProductEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Data
public class HomeProductWithTypeDto {

    private final String type;
    @EqualsAndHashCode.Exclude
    private final List<HomeProductEntity> products;

    HomeProductWithTypeDto(String aType) {
        type = aType;
        products = new ArrayList<>();
    }

    void addProd(HomeProductEntity aProdEntity){
        products.add(aProdEntity);
    }
}