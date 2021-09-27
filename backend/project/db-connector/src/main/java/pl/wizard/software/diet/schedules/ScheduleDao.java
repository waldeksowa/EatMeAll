package pl.wizard.software.diet.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {

    @Query(value = "select s.* from schedules s " +
            "left join members m on s.member_id = m.id " +
            "where m.account_id = ?1", nativeQuery = true)
    List<ScheduleEntity> findAll(Long accountId);

    @Query(value = "select s.* from schedules s " +
            "left join members m on s.member_id = m.id " +
            "where m.account_id = ?1 and s.id = ?2", nativeQuery = true)
    Optional<ScheduleEntity> findById(Long accountId, Long scheduleId);

    @Query(value = "select s from ScheduleEntity s " +
            "left join MemberEntity m on s.memberId = m.Id " +
            "where m.accountId = ?1 and s.memberId = ?2" +
            "order by s.date DESC" +
            "limit 1", nativeQuery = true)
    Optional<ScheduleEntity> findByMember(Long accountId, Long memberId);
}
