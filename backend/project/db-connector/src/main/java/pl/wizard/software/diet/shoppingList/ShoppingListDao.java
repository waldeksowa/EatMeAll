package pl.wizard.software.diet.shoppingList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListDao extends JpaRepository<ShoppingListEntity, Long> {
}
