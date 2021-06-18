package pl.wizard.software.diet.meals;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.products.ProductEntity;

import javax.persistence.*;

@Entity
@Table(name = "MEALS_PRODUCTS")
@Data
public class MealProductEntity extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    private Integer amount;
    @Enumerated(EnumType.ORDINAL)
    private unitTypeEnum unit;

    public enum unitTypeEnum {
        FAKE, GRAMS
    }
}
