package pl.wizard.software.diet.products;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXCLUDED_PRODUCTS")
@Data
public class ExcludedProductEntity extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
