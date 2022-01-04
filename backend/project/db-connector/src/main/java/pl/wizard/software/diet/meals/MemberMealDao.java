package pl.wizard.software.diet.meals;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMealDao extends JpaRepository<MemberMealEntity, Long> {
}
