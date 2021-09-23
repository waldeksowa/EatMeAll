package pl.wizard.software.diet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleForDayDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long breakfast;
    private Long secondBreakfast;
    private Long lunch;
    private Long dinner;
    private Long supper;
}
