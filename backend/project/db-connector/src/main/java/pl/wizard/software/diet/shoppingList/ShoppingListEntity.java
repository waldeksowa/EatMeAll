package pl.wizard.software.diet.shoppingList;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.products.ProductEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SHOPPING_LISTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListEntity extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    private int amount;
    private Date shoppingListDate;
    private boolean isBuyed;
    private Long accountId;
}
