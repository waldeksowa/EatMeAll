package pl.wizard.software.diet.schedules;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {

    @Query("select s from ScheduleEntity s " +
            "join s.member m " +
            "where m.accountId = :accountId")
    List<ScheduleEntity> findAll(@Param("accountId") Long accountId);

    @Query("select s from ScheduleEntity s " +
            "join s.member m " +
            "where m.accountId = :accountId and s.id = :scheduleId")
    Optional<ScheduleEntity> findById(@Param("accountId") Long accountId, @Param("scheduleId") Long scheduleId);

    @Query("select s from ScheduleEntity s " +
            "join s.member m " +
            "where m.accountId = :accountId and m.id = :memberId " +
            "order by s.scheduleDate desc")
    List<ScheduleEntity> findByMember(@Param("accountId") Long accountId, @Param("memberId") Long memberId, Pageable pageable);
}
