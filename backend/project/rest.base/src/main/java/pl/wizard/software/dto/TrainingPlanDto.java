package pl.wizard.software.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPlanDto {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private int version;
    private LocalDate trainingPlanDate;
    private Long memberId;
    private List<TrainingPlanItemDto> trainings;
}
