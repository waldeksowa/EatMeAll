package pl.wizard.software.diet.shoppingList;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "SHOPPING_LISTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListEntity extends AbstractBaseEntity {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "shopping_list_id")
    Set<ShoppingListItemEntity> items;
    private Date shoppingListDate;
    private Long accountId;
}
