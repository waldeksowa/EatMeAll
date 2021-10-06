package pl.wizard.software.diet.schedules;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {

    @Query("select s from MemberEntity m " +
            "join m.schedules s " +
            "where m.accountId = :accountId")
    List<ScheduleEntity> findAll(@Param("accountId") Long accountId);

    @Query("select s from MemberEntity m " +
            "join m.schedules s " +
            "where m.accountId = :accountId and s.id = :scheduleId")
    Optional<ScheduleEntity> findById(@Param("accountId") Long accountId, @Param("scheduleId") Long scheduleId);

//    @Query(value = "select s.* from schedules s " +
//            "left join members m on s.member_id = m.id " +
//            "where m.account_id = ?1 and s.member_id = ?2 " +
//            "order by s.schedule_date DESC " +
//            "limit 1", nativeQuery = true)
    @Query("select s from MemberEntity m " +
            "join m.schedules s " +
            "where m.accountId = :accountId and s.memberId = :memberId ")
    List<ScheduleEntity> findByMMMember(@Param("accountId") Long accountId, @Param("memberId") Long memberId, Pageable pageable);
}
