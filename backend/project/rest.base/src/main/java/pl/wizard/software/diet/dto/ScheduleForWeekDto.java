package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ScheduleForWeekDto {
    private LocalDate scheduleDate;
    private List<ScheduleForDayDto> schedule;
    private Long memberId;
}
