package pl.wizard.software.diet.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {
}
