package pl.wizard.software.diet.shoppingList;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.products.ProductEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPPING_LIST_ITEMS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItemEntity extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    private int amount;
    private boolean isBuyed;
}
