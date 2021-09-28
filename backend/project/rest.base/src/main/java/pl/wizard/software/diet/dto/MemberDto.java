package pl.wizard.software.diet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.wizard.software.diet.products.ExcludedProductEntity;

import java.util.Set;

@Getter
@Setter
@Builder
public class MemberDto {
    private Long id;
    private String name;
    private Integer age;
    private Double currentWeight;
    private Double currentFat;
    private Double currentMussels;
    private Double currentWater;
    private Double recommendedCalories;
    private Double recommendedCarbohydrates;
    private Double recommendedFat;
    private Double recommendedProtein;
    private Double recommendedRoughage;
    private Set<ScheduleForWeekDto> schedules;
    private Set<ExcludedProductEntity> excludedProducts;
    private Long accountId;
}
