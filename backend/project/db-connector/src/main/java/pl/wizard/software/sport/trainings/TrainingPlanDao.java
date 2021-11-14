package pl.wizard.software.sport.trainings;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TrainingPlanDao extends JpaRepository<TrainingPlanEntity, Long> {

    @Query("select t from TrainingPlanEntity t " +
            "join t.member m " +
            "where m.accountId = :accountId and " +
            "m.id = :memeberId")
    List<TrainingPlanEntity> findAllByMember(@Param("accountId") Long accountId,@Param("memeberId") Long memeberId);

    @Query("select t from TrainingPlanEntity t " +
            "join t.member m " +
            "where m.accountId = :accountId and " +
            "m.id = :memeberId and " +
            "t.trainingPlanDate = :date")
    List<TrainingPlanEntity> findByTrainingDate(@Param("accountId") Long accountId,
                                                @Param("memeberId") Long memberId,
                                                @Param("date") LocalDate date, Pageable pageable);

    @Query("select t from TrainingPlanEntity t " +
            "join t.member m " +
            "where m.accountId = :accountId and " +
            "m.id = :memberId and " +
            "t.id = :trainingPlanId")
    List<TrainingPlanEntity> findByIdAndMember(@Param("accountId") Long accountId,
                                               @Param("memberId") Long memberId,
                                               @Param("trainingPlanId") Long trainingPlanId);
}
