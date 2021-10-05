package pl.wizard.software.diet.members;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends AbstractBaseEntity {

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
    private Long accountId;
}
