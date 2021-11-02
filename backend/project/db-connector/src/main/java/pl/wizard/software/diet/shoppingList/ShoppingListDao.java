package pl.wizard.software.diet.shoppingList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingListDao extends JpaRepository<ShoppingListEntity, Long> {
    @Query("select s from ShoppingListEntity s " +
            "join s.account a " +
            "where a.id = :accountId and s.id = :shoppingListId")
    Optional<ShoppingListEntity> findByIdAndAccount(@Param("accountId") Long accountId, @Param("shoppingListId") Long shoppingListId);

    @Query("select s from ShoppingListEntity s " +
            "join s.account a " +
            "where a.id = :accountId " +
            "order by s.shoppingListDate desc")
    List<ShoppingListEntity> findCurrent(@Param("accountId") Long accountId, Pageable pageable);

}
