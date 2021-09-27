package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class ScheduleForWeekDto {
    private Long Id;
    private Date createdAt;
    private Date updatedAt;
    private int version;
    private Date scheduleDate;
    private List<ScheduleForDayDto> schedule;
    private Long memberId;
}
