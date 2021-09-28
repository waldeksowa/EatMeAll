package pl.wizard.software.diet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForWeekDto {
    private Long Id;
    private Date createdAt;
    private Date updatedAt;
    private int version;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date scheduleDate;
    private List<ScheduleForDayDto> schedule;
    private Long memberId;
}
