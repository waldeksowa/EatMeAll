package pl.wizard.software.diet.members;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import javax.persistence.*;
import java.util.Set;

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
    private Double currentWeight;
    private Double currentFat;
    private Double currentMussels;
    private Double currentWater;
    private Double recommendedCalories;
    private Double recommendedCarbohydrates;
    private Double recommendedFat;
    private Double recommendedProtein;
    private Double recommendedRoughage;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_Id")
    private Set<ScheduleEntity> schedules;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "member_id")
    private Set<ExcludedProductEntity> excludedProducts;
    @Column(name = "account_id")
    private Long accountId;
}
