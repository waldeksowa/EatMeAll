package pl.wizard.software.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDto {
    private Long id;
    private String name;
    private Integer age;
    private Integer height;
    private Double currentWeight;
    private Double currentFat;
    private Double currentMussels;
    private Double currentWater;
    private Double recommendedCalories;
    private Double recommendedCarbohydrates;
    private Double recommendedFat;
    private Double recommendedProtein;
    private Double recommendedRoughage;
    private ScheduleForWeekDto schedule;
    private Long accountId;
}
