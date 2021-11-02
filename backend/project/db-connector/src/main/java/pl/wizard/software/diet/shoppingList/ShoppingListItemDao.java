package pl.wizard.software.diet.shoppingList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListItemDao extends JpaRepository<ShoppingListItemEntity, Long> {
}
