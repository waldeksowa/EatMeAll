package pl.wizard.software.diet.shoppingList;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.login.AccountEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    List<ShoppingListItemEntity> items;
    private Date shoppingListDate;
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
