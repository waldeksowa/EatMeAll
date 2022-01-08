package pl.wizard.software.diet.meals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealDao extends JpaRepository<MealEntity, Long> {

    @Query(value = "select m.*" +
                    " from meals m" +
                    " left join meal_time mt on m.id = mt.meal_entity_id" +
                    " left join member_meals mm on m.id = mm.id" +
                    " where mt.meal_time = ?1" +
                    " order by random()" +
                    " limit ?2", nativeQuery = true)
    public List<MealEntity> findRandomByMealTime(int mealTime, int amount);
}
