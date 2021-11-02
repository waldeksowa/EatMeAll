package pl.wizard.software.diet.shoppingList;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.meals.SpecialAmountEnum;
import pl.wizard.software.diet.products.ProductEntity;

import javax.persistence.*;

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
    private int specialAmount;
    @Enumerated(value = EnumType.ORDINAL)
    private SpecialAmountEnum specialAmountUnit;
    private boolean isBuyed;
}
