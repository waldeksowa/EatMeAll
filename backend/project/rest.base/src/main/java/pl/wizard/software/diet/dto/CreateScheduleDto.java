package pl.wizard.software.diet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateScheduleDto {
    private Long memberId;
    private List<CreateScheduleForDayDto> schedule;
}
