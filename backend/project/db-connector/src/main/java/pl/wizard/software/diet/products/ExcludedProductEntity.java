package pl.wizard.software.diet.products;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "EXCLUDED_PRODUCTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcludedProductEntity extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    private Long memberId;
}
