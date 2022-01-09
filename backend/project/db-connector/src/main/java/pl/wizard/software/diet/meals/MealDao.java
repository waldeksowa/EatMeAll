package pl.wizard.software.diet.meals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealDao extends JpaRepository<MealEntity, Long> {

    @Query(value = "select ml.*, mt.*, st.*, mp.*, pr.*, mm.*, mb.*, sh.*, ep.*, epr.*, pml.*, pmt.*, pst.*, pmp.*, ppr.* " +
            " from meals ml " +
            " left outer join meal_time mt on ml.id = mt.meal_entity_id " +
            " left outer join steps st on ml.id = st.meal_id " +
            " left outer join meals_products mp on ml.id = mp.meal_id " +
            " left outer join products pr on mp.product_id = pr.id " +

            " left outer join member_meals mm on ml.id = mm.id " +

            " left outer join members mb on mm.member_id = mb.id " +
            " left outer join schedules sh on mb.id = sh.member_id " +
            " left outer join excluded_products ep on mb.id = ep.member_id " +
            " left outer join products epr on ep.product_id = epr.id " +

            " left outer join meals pml on mm.parent_meal_id = pml.id " +
            " left outer join meal_time pmt on pml.id = pmt.meal_entity_id " +
            " left outer join steps pst on pml.id = pst.meal_id " +
            " left outer join meals_products pmp on pml.id = pmp.meal_id " +
            " left outer join products ppr on pmp.product_id = ppr.id ", nativeQuery = true)
    public List<MealEntity> findRandomByMealTime(int mealTime, int amount);

    @Query(value = "select m.* " +
            " from meals m " +
            " left join meal_time mt on m.id = mt.meal_entity_id " +
            " where mt.meal_time = ?1 " +
            " order by random() " +
            " limit ?2 ", nativeQuery = true)
    public List<MealEntity> findRandomByMealTimeTest(int mealTime, int amount);
}
