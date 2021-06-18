package pl.wizard.software.diet.meals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealDao extends JpaRepository<MealEntity, Long> {

    @Query("Select m From MealEntity m")
    public List<MealEntity> findAllMeals();

}
