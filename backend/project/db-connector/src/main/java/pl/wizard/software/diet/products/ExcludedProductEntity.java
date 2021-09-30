package pl.wizard.software.diet.products;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "EXCLUDED_PRODUCTS")
@Data
public class ExcludedProductEntity extends AbstractBaseEntity {

    @Column(name = "product_id")
    private Long productId;
    @Column(name = "member_id")
    private Long memberId;

}
