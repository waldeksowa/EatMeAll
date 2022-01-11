package pl.wizard.software.diet.meals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberMealDao extends JpaRepository<MemberMealEntity, Long> {

    @Query("select m from MemberMealEntity m " +
            "join m.member mb " +
            "join m.parentMeal pm " +
            "where mb.id = :memberId and pm.id = :parentMealId")
    Optional<MemberMealEntity> findByMemberAndParentMeal(@Param("memberId") Long memberId, @Param("parentMealId") Long parentMealId);
}
