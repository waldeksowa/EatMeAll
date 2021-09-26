package pl.wizard.software.diet.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {

    @Query("select s from ScheduleEntity s " +
            "left join MemberEntity m on s.memberId = m.Id" +
            "where m.accountId = ?1")
    List<ScheduleEntity> findAllSchedules(Long accountId);

    @Query("select s from ScheduleEntity s " +
            "left join MemberEntity m on s.memberId = m.Id" +
            "where m.accountId = ?1 and s.Id = ?2")
    Optional<ScheduleEntity> findById(Long accountId, Long scheduleId);

    @Query(value = "select s from ScheduleEntity s " +
            "left join MemberEntity m on s.memberId = m.Id" +
            "where m.accountId = ?1 and s.memberId = ?2" +
            "order by s.date DESC" +
            "limit 1", nativeQuery = true)
    Optional<ScheduleEntity> findByMember(Long accountId, Long memberId);
}
