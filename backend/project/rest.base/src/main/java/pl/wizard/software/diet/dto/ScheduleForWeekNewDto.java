package pl.wizard.software.diet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.wizard.software.dto.ScheduleForDayDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForWeekNewDto {
    private Long Id;
    private Date createdAt;
    private Date updatedAt;
    private int version;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;
    private List<ScheduleForDayDto> schedule = new ArrayList<>();
    private Long memberId;
}
