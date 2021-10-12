package pl.wizard.software.diet.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface MemberDao extends JpaRepository<MemberEntity, Long> {

    @Query("select m.id, m.name, m.age, m.height, m.currentWeight, m.currentFat, m.currentMussels, m.currentWater, " +
                "m.recommendedCalories, m.recommendedCarbohydrates, m.recommendedFat, m.recommendedProtein, m.recommendedRoughage, m.accountId " +
            "from MemberEntity m " +
            "where m.accountId = :accountId")
    List<Tuple> findAllMembers(@Param("accountId") Long accountId);

    @Query("select m from MemberEntity m " +
            "left join fetch m.schedules s " +
            "where m.accountId = :accountId and m.id = :memberId")
    Optional<MemberEntity> findMemberById(@Param("accountId") Long accountId, @Param("memberId") Long memberId);
}
