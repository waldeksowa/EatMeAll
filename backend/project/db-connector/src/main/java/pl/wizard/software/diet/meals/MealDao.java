package pl.wizard.software.diet.meals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealDao extends JpaRepository<MealEntity, Long> {

    @Query(value = "" +
            "select *, 0 as clazz_ " +
            "from meals m " +
            "left join meal_time mt on m.id = mt.meal_entity_id " +
            "left join member_meals mm on mm.id = m.id " +
            "where mt.meal_time = :mealTime " +
            "    and mm.id is null " +
            "order by random() " +
            "limit :amount ", nativeQuery = true)
    List<MealEntity> findRandomByMealTime(@Param("mealTime") int mealTime, @Param("amount") int amount);
}
