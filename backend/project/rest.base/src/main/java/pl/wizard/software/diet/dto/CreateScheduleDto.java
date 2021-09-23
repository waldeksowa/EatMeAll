package pl.wizard.software.diet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateScheduleDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<ScheduleForDayDto> meals;
}
